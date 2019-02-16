package de.clashsoft.gentreesrc;

import de.clashsoft.gentreesrc.tree.DefinitionFile;
import de.clashsoft.gentreesrc.tree.Property;
import de.clashsoft.gentreesrc.tree.PropertyStyle;
import de.clashsoft.gentreesrc.tree.TypeDeclaration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Generator
{
	public static void generate(DefinitionFile definitionFile, String targetDirectory) throws IOException
	{
		for (TypeDeclaration decl : definitionFile.getDeclarations())
		{
			generate(decl, targetDirectory);
		}
	}

	private static void generate(TypeDeclaration decl, String targetDirectory) throws IOException
	{
		for (TypeDeclaration subDecl : decl.getSubTypes())
		{
			generate(subDecl, targetDirectory);
		}

		final String content = generate(decl);

		final String fileName =
			targetDirectory + '/' + decl.getPackageName().replace('.', '/') + '/' + decl.getClassName() + ".java";
		final Path path = Paths.get(fileName);
		Files.createDirectories(path.getParent());
		Files.write(path, content.getBytes(StandardCharsets.UTF_8));
	}

	private static String generate(TypeDeclaration decl)
	{
		Set<String> imports = new HashSet<>();
		StringBuilder classBody = new StringBuilder();
		StringBuilder implClassBody = new StringBuilder();
		final StringBuilder visitorClassBody = new StringBuilder();

		generateOfMethod(decl, classBody);

		if (!decl.getProperties().isEmpty())
		{
			if (decl.getProperties().stream().anyMatch(p -> p.getStyle() == PropertyStyle.LIST))
			{
				imports.add("java.util.List");
				imports.add("java.util.ArrayList");
			}

			generateProperties(decl, classBody);
			generateImplFields(decl, implClassBody);
			generateImplConstructor(decl, implClassBody);
			generateImplProperties(decl, implClassBody);
		}

		generateAcceptMethods(decl, classBody);
		generateAcceptImpls(decl, implClassBody);

		generateVisitorMethods(decl, visitorClassBody);

		// classes

		final String className = decl.getClassName();
		final String superClass = decl.getSuperType() != null ? decl.getSuperType().getClassName() : null;

		classBody.append(Templates.sectionComment("Classes"));

		// generate impl class

		final String implClass = Templates.treeImplClass(className, superClass, implClassBody.toString());
		classBody.append(implClass);

		// generate visitor class
		final List<String> visitorSuperInterfaces = decl.getSubTypes().stream().map(TypeDeclaration::getClassName)
		                                                .collect(Collectors.toList());
		final String visitorClass = Templates.visitorInterface(className, visitorSuperInterfaces,
		                                                       visitorClassBody.toString());
		classBody.append(visitorClass);

		// generate main class

		final String packageName = decl.getPackageName();
		final String importText = imports.stream().map(Templates::importDeclaration).collect(Collectors.joining());

		return Templates.treeInterface(packageName, importText, className, superClass, classBody.toString());
	}

	private static String joinProperties(List<Property> properties, BinaryOperator<String> operator, String separator)
	{
		return properties.stream().map(p -> operator.apply(p.getName(), p.getType()))
		                 .collect(Collectors.joining(separator));
	}

	private static void generateOfMethod(TypeDeclaration decl, StringBuilder classBody)
	{
		classBody.append(Templates.sectionComment("Static Methods"));

		final String parameters = joinProperties(decl.getProperties(), Templates::parameter, ", ");
		final String arguments = decl.getProperties().stream().map(Property::getName).collect(Collectors.joining(", "));

		classBody.append(Templates.ofMethod(decl.getClassName(), parameters, arguments));
	}

	private static void generateProperties(TypeDeclaration decl, StringBuilder classBody)
	{
		classBody.append(Templates.sectionComment("Properties"));

		for (Property property : decl.getProperties())
		{
			classBody.append(Templates.getter(property.getName(), property.getType()));

			if (property.getStyle() != PropertyStyle.LIST)
			{
				classBody.append(Templates.setter(property.getName(), property.getType()));
			}
		}
	}

	private static void generateAcceptMethods(TypeDeclaration decl, StringBuilder classBody)
	{
		classBody.append(Templates.sectionComment("Methods"));

		classBody.append(Templates.acceptMethod(decl.getClassName(), decl.getClassName()));
	}

	private static void generateImplConstructor(TypeDeclaration decl, StringBuilder implClassBody)
	{
		implClassBody.append(Templates.sectionComment("Constructors"));

		// no-arg constructor
		implClassBody.append(Templates.constructor("Impl", "", ""));

		final String parameters = joinProperties(decl.getProperties(), Templates::parameter, ", ");
		final String body = joinProperties(decl.getProperties(), Templates::setThis, "\n");

		implClassBody.append(Templates.constructor("Impl", parameters, body));
	}

	private static void generateImplFields(TypeDeclaration decl, StringBuilder implClassBody)
	{
		implClassBody.append(Templates.sectionComment("Fields"));

		for (Property property : decl.getProperties())
		{
			implClassBody.append(Templates.field(property.getName(), property.getType()));
		}
	}

	private static void generateImplProperties(TypeDeclaration decl, StringBuilder implClassBody)
	{
		implClassBody.append(Templates.sectionComment("Properties"));

		for (Property property : decl.getProperties())
		{
			implClassBody.append(Templates.getterImpl(property.getName(), property.getType()));

			if (property.getStyle() != PropertyStyle.LIST)
			{
				implClassBody.append(Templates.setterImpl(property.getName(), property.getType()));
			}
		}
	}

	private static void generateAcceptImpls(TypeDeclaration decl, StringBuilder implClassBody)
	{
		implClassBody.append(Templates.sectionComment("Methods"));

		implClassBody.append(Templates.acceptMethodImpl(decl.getClassName(), decl.getClassName()));
	}

	private static void generateVisitorMethods(TypeDeclaration decl, StringBuilder visitorClassBody)
	{
		visitorClassBody.append(Templates.sectionComment("Methods"));

		generateVisitorMethodsRecursively(0, decl, visitorClassBody);
	}

	private static final int    MAX_TABS = 64;
	private static       char[] tabs     = new char[MAX_TABS];

	static
	{
		Arrays.fill(tabs, '\t');
	}

	private static void generateVisitorMethodsRecursively(int level, TypeDeclaration decl,
		StringBuilder visitorClassBody)
	{
		visitorClassBody.append(tabs, 0, Math.min(MAX_TABS, level));
		visitorClassBody.append(Templates.visitMethod(level != 0, decl.getClassName()));

		for (TypeDeclaration subType : decl.getSubTypes())
		{
			generateVisitorMethodsRecursively(level + 1, subType, visitorClassBody);
		}
	}
}
