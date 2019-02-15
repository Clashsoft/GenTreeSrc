package de.clashsoft.gentreesrc;

import de.clashsoft.gentreesrc.antlr.GenTreeSrcLexer;
import de.clashsoft.gentreesrc.antlr.GenTreeSrcParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

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

			final GenTreeSrcListener listener = new GenTreeSrcListener(descriptionFile, targetDirectory);

			ParseTreeWalker.DEFAULT.walk(listener, parser.main());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
