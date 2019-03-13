package de.clashsoft.gentreesrc;

import de.clashsoft.gentreesrc.antlr.GenTreeSrcLexer;
import de.clashsoft.gentreesrc.antlr.GenTreeSrcParser;
import de.clashsoft.gentreesrc.tree.DefinitionFile;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.ArrayList;

public final class Parser
{
	// no instances
	private Parser()
	{
	}

	public static DefinitionFile parse(String descriptionFile) throws IOException
	{
		final GenTreeSrcLexer lexer = new GenTreeSrcLexer(CharStreams.fromFileName(descriptionFile));
		final GenTreeSrcParser parser = new GenTreeSrcParser(new CommonTokenStream(lexer));

		final DefinitionFile definitionFile = DefinitionFile.of(new ArrayList<>(), new ArrayList<>());

		ParseTreeWalker.DEFAULT.walk(new ASTListener(definitionFile), parser.main());

		return definitionFile;
	}
}
