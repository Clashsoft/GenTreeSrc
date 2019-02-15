package de.clashsoft.gentreesrc;

import de.clashsoft.gentreesrc.antlr.GenTreeSrcBaseListener;

public class GenTreeSrcListener extends GenTreeSrcBaseListener
{
	// =============== Fields ===============

	// --------------- Constructor Parameters ---------------

	private final String descriptionFile;
	private final String targetDirectory;

	// =============== Constructors ===============

	public GenTreeSrcListener(String descriptionFile, String targetDirectory)
	{
		this.descriptionFile = descriptionFile;
		this.targetDirectory = targetDirectory;
	}
}
