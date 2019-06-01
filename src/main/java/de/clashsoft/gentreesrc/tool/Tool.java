package de.clashsoft.gentreesrc.tool;

import de.clashsoft.gentreesrc.codegen.Generator;
import de.clashsoft.gentreesrc.antlr.Parser;
import de.clashsoft.gentreesrc.tree.DefinitionFile;
import org.apache.commons.cli.*;

import javax.lang.model.SourceVersion;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Tool implements javax.tools.Tool
{
	// =============== Constants ===============

	private static final String TOOL_NAME      = "gentreesrc";
	private static final String USAGE          = TOOL_NAME + " <options> <inputDirs...>";
	private static final String FILE_EXTENSION = ".gts";
	private static final String HEADER         = "options:";

	// =============== Fields ===============

	private final Config      config = new Config();
	private       PrintWriter out;
	private       PrintWriter err;

	// =============== Properties ===============

	public PrintWriter getOut()
	{
		return this.out;
	}

	public void setOut(PrintWriter out)
	{
		this.out = out;
	}

	public PrintWriter getErr()
	{
		return this.err;
	}

	public void setErr(PrintWriter err)
	{
		this.err = err;
	}

	@Override
	public Set<SourceVersion> getSourceVersions()
	{
		return Collections.singleton(SourceVersion.RELEASE_8);
	}

	// =============== Methods ===============

	@Override
	public int run(InputStream in, OutputStream out, OutputStream err, String... arguments)
	{
		this.setOut(new PrintWriter(out));
		this.setErr(new PrintWriter(err));

		final int result = this.run(arguments);

		this.getOut().flush();
		this.getErr().flush();

		return result;
	}

	private int run(String[] arguments)
	{
		Options options = this.config.createOptions();

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try
		{
			cmd = parser.parse(options, arguments);
		}
		catch (ParseException e)
		{
			this.getOut().println(e.getMessage());
			formatter.printHelp(this.getOut(), 80, USAGE, HEADER, options, 2, 1, "");
			return 1;
		}

		this.config.readOptions(cmd);
		return this.run();
	}

	private int run()
	{
		final Set<File> generatedFiles = new HashSet<>();

		for (String inputDirName : this.config.getInputDirs())
		{
			final File inputFile = new File(inputDirName);
			if (inputFile.exists())
			{
				this.process(inputFile, generatedFiles);
			}
		}

		if (this.config.isDeleteOld())
		{
			for (File child : Objects.requireNonNull(new File(this.config.getOutputDir()).listFiles()))
			{
				this.deleteOld(child, generatedFiles);
			}
		}

		return 0;
	}

	private void process(File inputFile, Set<File> generatedFiles)
	{
		if (inputFile.isDirectory())
		{
			for (File child : Objects.requireNonNull(inputFile.listFiles()))
			{
				this.process(child, generatedFiles);
			}

			return;
		}

		if (inputFile.getName().endsWith(FILE_EXTENSION))
		{
			this.processDefinition(inputFile, generatedFiles);
		}
	}

	private void processDefinition(File inputFile, Set<File> generatedFiles)
	{
		try
		{
			final DefinitionFile definitionFile = Parser.parse(inputFile.getAbsolutePath());
			Generator.generate(this.config, definitionFile, generatedFiles);
		}
		catch (Exception e)
		{
			e.printStackTrace(this.err);
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	private void deleteOld(File file, Set<File> generatedFiles)
	{
		if (file.isDirectory())
		{
			for (File child : Objects.requireNonNull(file.listFiles()))
			{
				this.deleteOld(child, generatedFiles);
			}

			// delete empty directory AFTER deleting children
			if (Objects.requireNonNull(file.list()).length == 0)
			{
				file.delete();
			}

			return;
		}

		if (!generatedFiles.contains(file))
		{
			file.delete();
		}
	}
}
