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
import java.util.HashSet;
import java.util.Set;
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

		if (!decl.getProperties().isEmpty())
		{
			if (decl.getProperties().stream().anyMatch(p -> p.getStyle() == PropertyStyle.LIST))
			{
				imports.add("java.util.List");
				imports.add("java.util.ArrayList");
			}

			generateProperties(decl, classBody);
			generateImplFields(decl, implClassBody);
			generateImplProperties(decl, implClassBody);
		}

		// generate impl and main class

		final String packageName = decl.getPackageName();

		final String importText = imports.stream().map(Templates::importDeclaration).collect(Collectors.joining());

		final String className = decl.getClassName();
		final String superClass = decl.getSuperType() != null ? decl.getSuperType().getClassName() : "";

		final String implClass = Templates.treeImplClass(className, superClass, implClassBody.toString());
		classBody.append(Templates.sectionComment("Classes"));
		classBody.append(implClass);

		return Templates.treeInterface(packageName, importText, className, superClass, classBody.toString());
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
}
