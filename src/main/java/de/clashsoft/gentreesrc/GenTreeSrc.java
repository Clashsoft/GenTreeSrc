package de.clashsoft.gentreesrc;

import de.clashsoft.gentreesrc.antlr.GenTreeSrcLexer;
import de.clashsoft.gentreesrc.antlr.GenTreeSrcParser;
import de.clashsoft.gentreesrc.tree.DefinitionFile;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.ArrayList;

public class GenTreeSrc
{
	public static void main(String[] args)
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

	public static void genTreeSrc(String descriptionFile, String targetDirectory, String language)
	{
		try
		{
			final GenTreeSrcLexer lexer = new GenTreeSrcLexer(CharStreams.fromFileName(descriptionFile));
			final GenTreeSrcParser parser = new GenTreeSrcParser(new CommonTokenStream(lexer));

			final DefinitionFile definitionFile = DefinitionFile.of(new ArrayList<>(), new ArrayList<>());

			ParseTreeWalker.DEFAULT.walk(new ASTListener(definitionFile), parser.main());

			Generator.generate(definitionFile, targetDirectory, language);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
