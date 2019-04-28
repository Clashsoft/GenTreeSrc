package de.clashsoft.gentreesrc;

import de.clashsoft.gentreesrc.tool.Config;
import de.clashsoft.gentreesrc.tree.DefinitionFile;
import de.clashsoft.gentreesrc.tree.decl.TypeDecl;
import de.clashsoft.gentreesrc.util.GTSStringRenderer;
import de.clashsoft.gentreesrc.util.ImportHelper;
import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Generator
{
	// =============== Fields ===============

	private final Config              config;
	private final Map<String, String> importMap;
	private final STGroup             treeGroup;

	// =============== Constructors ===============

	private Generator(Config config, Map<String, String> importMap, STGroup treeGroup)
	{
		this.config = config;
		this.importMap = importMap;
		this.treeGroup = treeGroup;
	}

	// =============== Static Methods ===============

	public static void generate(Config config, DefinitionFile definitionFile, Set<File> generatedFiles)
		throws IOException
	{
		// tree group

		final STGroup treeGroup = new STGroupFile(
			Generator.class.getResource("templates/" + config.getLanguage() + ".stg"));
		treeGroup.registerRenderer(String.class, new GTSStringRenderer());

		// import map

		final Map<String, String> importMap = new HashMap<>();
		ImportHelper.collectImportMap(definitionFile, importMap);

		// generate

		final Generator generator = new Generator(config, importMap, treeGroup);

		for (TypeDecl decl : definitionFile.getDeclarations())
		{
			generator.generate(decl, generatedFiles);
		}
	}

	// =============== Methods ===============

	private void generate(TypeDecl decl, Set<File> generatedFiles) throws IOException
	{
		for (TypeDecl subDecl : decl.getSubTypes())
		{
			this.generate(subDecl, generatedFiles);
		}

		// imports

		final Set<String> imports = new TreeSet<>();
		ImportHelper.collectImports(this.importMap, decl, imports);

		// target file

		final String fileName = this.treeGroup.getInstanceOf("fileName").add("typeDecl", decl).render();
		final File file = new File(this.config.getOutputDir(), fileName);

		//noinspection ResultOfMethodCallIgnored
		file.getParentFile().mkdirs();

		// main class

		final ST treeClass = this.treeGroup.getInstanceOf("treeClass");
		treeClass.add("config", this.config);
		treeClass.add("typeDecl", decl);
		treeClass.add("imports", imports);

		try (Writer writer = new BufferedWriter(new FileWriter(file)))
		{
			// using write with a FileWriter is better than rendering to a String and then writing that,
			// because it does not require materializing the entire text in memory
			treeClass.write(new AutoIndentWriter(writer));
		}

		generatedFiles.add(file);
	}
}
