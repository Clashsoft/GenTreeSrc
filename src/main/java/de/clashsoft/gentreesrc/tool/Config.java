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

	private boolean deleteOld;
	private boolean visitPar;
	private boolean visitRet;
	private boolean visitDefault;
	private boolean visitParent;

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

	public boolean isDeleteOld()
	{
		return this.deleteOld;
	}

	public void setDeleteOld(boolean deleteOld)
	{
		this.deleteOld = deleteOld;
	}

	public boolean isVisitPar()
	{
		return this.visitPar;
	}

	public void setVisitPar(boolean visitPar)
	{
		this.visitPar = visitPar;
	}

	public boolean isVisitRet()
	{
		return this.visitRet;
	}

	public void setVisitRet(boolean visitRet)
	{
		this.visitRet = visitRet;
	}

	public boolean isVisitDefault()
	{
		return this.visitDefault;
	}

	public void setVisitDefault(boolean visitDefault)
	{
		this.visitDefault = visitDefault;
	}

	public boolean isVisitParent()
	{
		return this.visitParent;
	}

	public void setVisitParent(boolean visitParent)
	{
		this.visitParent = visitParent;
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

		options.addOption(new Option("d", "delete-old", false, "delete old files in output directory"));

		options.addOption(
			new Option(null, "visit-par", false, "add a 'P par' parameter to visit and accept methods (default)"));
		options.addOption(
			new Option(null, "no-visit-par", false, "do not add 'P par' parameter to visit and accept methods"));
		options.addOption(
			new Option(null, "visit-return", false, "make visit and accept methods return a generic type R (default)"));
		options.addOption(new Option(null, "visit-void", false, "make visit and accept methods return void"));

		options.addOption(new Option(null, "visit-default", false,
		                             "visit methods generate default implementations that throw an UnsupportedOperationException"));

		options.addOption(new Option(null, "visit-parent", false,
		                             "visit methods generate default implementations that delegate to the visit method of the parent class"));

		return options;
	}

	public void readOptions(CommandLine cmd)
	{
		this.setLanguage(cmd.getOptionValue("language", "java").toLowerCase());
		this.setOutputDir(cmd.getOptionValue("outputDir", "src/main/" + this.getLanguage()));
		this.getInputDirs().addAll(cmd.getArgList());
		this.setDeleteOld(cmd.hasOption("delete-old"));

		this.setVisitPar(!cmd.hasOption("no-visit-par"));
		this.setVisitRet(!cmd.hasOption("visit-void"));

		this.setVisitDefault(cmd.hasOption("visit-default"));
		this.setVisitParent(cmd.hasOption("visit-parent"));
	}
}
