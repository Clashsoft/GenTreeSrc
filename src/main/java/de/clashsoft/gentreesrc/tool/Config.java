package de.clashsoft.gentreesrc.tool;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.List;

public class Config
{
	// =============== Fields ===============

	private String       language;
	private String       outputDir;
	private List<String> inputDirs = new ArrayList<>();

	// =============== Properties ===============

	public String getLanguage()
	{
		return this.language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getOutputDir()
	{
		return this.outputDir;
	}

	public void setOutputDir(String outputDir)
	{
		this.outputDir = outputDir;
	}

	public List<String> getInputDirs()
	{
		return this.inputDirs;
	}

	// =============== Methods ===============

	public Options createOptions()
	{
		final Options options = new Options();

		final Option language = new Option("l", "language", true, "target language, default: java");
		language.setRequired(false);
		options.addOption(language);

		final Option modelDir = new Option("o", "outputDir", true, "output directory, default: src/main/<language>");
		modelDir.setRequired(false);
		options.addOption(modelDir);

		return options;
	}

	public void readOptions(CommandLine cmd)
	{
		this.setLanguage(cmd.getOptionValue("language", "java"));
		this.setOutputDir(cmd.getOptionValue("outputDir", "src/main/" + this.getLanguage()));
		this.getInputDirs().addAll(cmd.getArgList());
	}
}
