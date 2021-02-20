package de.clashsoft.gentreesrc.antlr;

import de.clashsoft.gentreesrc.tree.DefinitionFile;
import de.clashsoft.gentreesrc.tree.Node;
import de.clashsoft.gentreesrc.tree.decl.*;
import de.clashsoft.gentreesrc.tree.type.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.*;

public class ASTListener extends GenTreeSrcBaseListener
{
	// =============== Fields ===============

	private DefinitionFile definitionFile;

	private TypeDecl currentDeclaration;

	private Deque<Node> stack = new ArrayDeque<>(2);

	// =============== Constructors ===============

	public ASTListener(DefinitionFile definitionFile)
	{
		this.definitionFile = definitionFile;
	}

	// =============== Static Methods ===============

	public static DefinitionFile parse(String descriptionFile) throws IOException
	{
		final GenTreeSrcLexer lexer = new GenTreeSrcLexer(CharStreams.fromFileName(descriptionFile));
		final GenTreeSrcParser parser = new GenTreeSrcParser(new CommonTokenStream(lexer));

		final DefinitionFile definitionFile = DefinitionFile.of(new ArrayList<>());

		ParseTreeWalker.DEFAULT.walk(new ASTListener(definitionFile), parser.main());

		return definitionFile;
	}

	// =============== Methods ===============

	// =============== Methods ===============

	private <T extends Node> void push(T value)
	{
		this.stack.push(value);
	}

	private <T> T pop()
	{
		return (T) this.stack.pop();
	}

	private <T> List<T> pop(Class<T> type, int count)
	{
		// strange logic, but end result is that the top of the stack ends up at the end of the list.
		final List<T> result = new ArrayList<>(Collections.nCopies(count, null));
		for (int i = count - 1; i >= 0; i--)
		{
			result.set(i, type.cast(this.stack.pop()));
		}
		return result;
	}

	@Override
	public void enterTypeDeclaration(GenTreeSrcParser.TypeDeclarationContext ctx)
	{
		final Attributes attributes = new Attributes();
		for (final GenTreeSrcParser.TypeModifierContext modCtx : ctx.typeModifier())
		{
			switch (modCtx.getStart().getType())
			{
			case GenTreeSrcLexer.ABSTRACT:
				attributes.add(Modifier.ABSTRACT);
				break;
			case GenTreeSrcLexer.IMPORT:
				attributes.add(Modifier.IMPORT);
				break;
			}
		}

		final String packageName = getPackageName(ctx.packageName());
		final String className = ctx.className.getText();

		TypeDecl parent = this.currentDeclaration;
		final String fullPackageName = getPackageName(parent, packageName);

		this.currentDeclaration = TypeDecl.of(attributes, fullPackageName, className, parent, new ArrayList<>(),
		                                      new ArrayList<>());

		if (parent != null)
		{
			parent.getSubTypes().add(this.currentDeclaration);
		}
		else
		{
			this.definitionFile.getDeclarations().add(this.currentDeclaration);
		}
	}

	private static String getPackageName(GenTreeSrcParser.PackageNameContext ctx)
	{
		final String packageText = ctx.getText();
		// strip trailing period
		return packageText.isEmpty() ? packageText : packageText.substring(0, packageText.length() - 1);
	}

	private static String getPackageName(TypeDecl parent, String child)
	{
		if (parent == null)
		{
			return child;
		}
		if (child.isEmpty())
		{
			return parent.getPackageName();
		}
		return parent.getPackageName() + '.' + child;
	}

	// --------------- Properties ---------------

	@Override
	public void exitProperty(GenTreeSrcParser.PropertyContext ctx)
	{
		final Attributes attributes = new Attributes();
		for (final GenTreeSrcParser.PropertyModifierContext modCtx : ctx.propertyModifier())
		{
			switch (modCtx.getStart().getType())
			{
			case GenTreeSrcLexer.DELEGATE:
				attributes.add(Modifier.DELEGATE);
				break;
			case GenTreeSrcLexer.READONLY:
				attributes.add(Modifier.READONLY);
				break;
			case GenTreeSrcLexer.NOCONSTRUCT:
				attributes.add(Modifier.NOCONSTRUCT);
				break;
			}
		}

		final Type type = this.pop();
		final String name = ctx.name.getText();
		final PropertyDecl property = PropertyDecl.of(this.currentDeclaration, attributes, name, type);
		this.currentDeclaration.getMembers().add(property);
	}

	// --------------- Methods ---------------

	@Override
	public void exitMethod(GenTreeSrcParser.MethodContext ctx)
	{
		final Type type = this.pop();
		final List<ParameterDecl> params = this.pop(ParameterDecl.class, ctx.parameter().size());
		final String name = ctx.name.getText();
		final Attributes attributes = new Attributes();
		final MethodDecl methodDecl = MethodDecl.of(this.currentDeclaration, attributes, name, params, type);
		this.currentDeclaration.getMembers().add(methodDecl);
	}

	@Override
	public void exitParameter(GenTreeSrcParser.ParameterContext ctx)
	{
		final Type type = this.pop();
		final String name = ctx.name.getText();
		final ParameterDecl parameterDecl = ParameterDecl.of(null, new Attributes(), name, type);
		this.push(parameterDecl);
	}

	// --------------- Types ---------------

	@Override
	public void exitNamedType(GenTreeSrcParser.NamedTypeContext ctx)
	{
		final String name = ctx.name.getText();
		final List<Type> args;
		final GenTreeSrcParser.GenericArgumentsContext genericArguments = ctx.genericArguments();
		if (genericArguments != null)
		{
			args = this.pop(Type.class, genericArguments.type().size());
		}
		else
		{
			args = null;
		}
		this.push(NamedType.of(name, args));
	}

	@Override
	public void exitListType(GenTreeSrcParser.ListTypeContext ctx)
	{
		this.push(ListType.of(this.pop()));
	}

	@Override
	public void exitMapType(GenTreeSrcParser.MapTypeContext ctx)
	{
		final Type valueType = this.pop();
		final Type keyType = this.pop();
		this.push(MapType.of(keyType, valueType));
	}

	@Override
	public void exitArrayTypeSuffix(GenTreeSrcParser.ArrayTypeSuffixContext ctx)
	{
		this.push(ArrayType.of(this.pop()));
	}

	@Override
	public void exitOptionalTypeSuffix(GenTreeSrcParser.OptionalTypeSuffixContext ctx)
	{
		this.push(OptionalType.of(this.pop()));
	}

	@Override
	public void exitTypeDeclaration(GenTreeSrcParser.TypeDeclarationContext ctx)
	{
		this.currentDeclaration = this.currentDeclaration.getSuperType();
	}
}
