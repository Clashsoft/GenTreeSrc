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
			System.err.println("usage: gentreesrc <descriptionFile> <targetDirectory>");
			return;
		}

		genTreeSrc(args[0], args[1]);
	}

	public static void genTreeSrc(String descriptionFile, String targetDirectory)
	{
		try
		{
			final GenTreeSrcLexer lexer = new GenTreeSrcLexer(CharStreams.fromFileName(descriptionFile));
			final GenTreeSrcParser parser = new GenTreeSrcParser(new CommonTokenStream(lexer));

			final DefinitionFile definitionFile = DefinitionFile.of(new ArrayList<>(), new ArrayList<>());

			ParseTreeWalker.DEFAULT.walk(new ASTListener(definitionFile), parser.main());

			Generator.generate(definitionFile, targetDirectory, "Java");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
