package de.clashsoft.gentreesrc;

import java.io.IOException;

public final class Main
{
	// no instances
	private Main()
	{
	}

	public static void main(String[] args) throws IOException
	{
		if (args.length < 2)
		{
			printUsage();
			return;
		}

		if (!"-l".equals(args[0]))
		{
			genTreeSrc(args[0], args[1], "Java");
			return;
		}

		if (args.length < 4)
		{
			printUsage();
			return;
		}

		genTreeSrc(args[2], args[3], args[1]);
		return;
	}

	private static void printUsage()
	{
		System.err.println("usage: gentreesrc <descriptionFile> <targetDirectory>");
		System.err.println("     | gentreesrc -l <language> <descriptionFile> <targetDirectory>");
	}

	public static void genTreeSrc(String descriptionFile, String targetDirectory, String language) throws IOException
	{
		Generator.generate(Parser.parse(descriptionFile), targetDirectory, language);
	}
}
