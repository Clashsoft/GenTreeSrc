package de.clashsoft.gentreesrc;

import de.clashsoft.gentreesrc.tree.DefinitionFile;
import de.clashsoft.gentreesrc.tree.TypeDeclaration;
import de.clashsoft.gentreesrc.util.ImportHelper;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Generator
{
	private static final STGroup treeGroup = new STGroupFile(Generator.class.getResource("templates/Java.stg"));

	static
	{
		treeGroup.registerRenderer(String.class, new StringRenderer());
	}

	public static void generate(DefinitionFile definitionFile, String targetDirectory) throws IOException
	{
		Map<String, String> importMap = new HashMap<>();
		ImportHelper.collectImportMap(definitionFile, importMap);

		for (TypeDeclaration decl : definitionFile.getDeclarations())
		{
			generate(importMap, decl, targetDirectory);
		}
	}

	private static void generate(Map<String, String> importMap, TypeDeclaration decl, String targetDirectory)
		throws IOException
	{
		for (TypeDeclaration subDecl : decl.getSubTypes())
		{
			generate(importMap, subDecl, targetDirectory);
		}

		final String content = generate(importMap, decl);

		final String fileName =
			targetDirectory + '/' + decl.getPackageName().replace('.', '/') + '/' + decl.getClassName() + ".java";
		final Path path = Paths.get(fileName);
		Files.createDirectories(path.getParent());
		Files.write(path, content.getBytes(StandardCharsets.UTF_8));
	}

	private static String generate(Map<String, String> importMap, TypeDeclaration decl)
	{
		// imports

		final Set<String> imports = new TreeSet<>();
		ImportHelper.collectImports(importMap, decl, imports);

		// generate main class

		final ST treeClass = treeGroup.getInstanceOf("treeClass");
		treeClass.add("typeDecl", decl);
		treeClass.add("imports", imports);
		return treeClass.render();
	}
}
