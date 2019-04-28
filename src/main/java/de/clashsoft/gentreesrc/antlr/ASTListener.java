package de.clashsoft.gentreesrc.antlr;

import de.clashsoft.gentreesrc.tree.DefinitionFile;
import de.clashsoft.gentreesrc.tree.decl.ImportDecl;
import de.clashsoft.gentreesrc.tree.decl.PropertyDecl;
import de.clashsoft.gentreesrc.tree.decl.TypeDecl;
import de.clashsoft.gentreesrc.tree.type.ListType;
import de.clashsoft.gentreesrc.tree.type.NamedType;
import de.clashsoft.gentreesrc.tree.type.OptionalType;
import de.clashsoft.gentreesrc.tree.type.Type;

import java.util.ArrayList;

public class ASTListener extends GenTreeSrcBaseListener
{
	// =============== Fields ===============

	private DefinitionFile definitionFile;

	private TypeDecl currentDeclaration;

	private Type type;

	// =============== Constructors ===============

	public ASTListener(DefinitionFile definitionFile)
	{
		this.definitionFile = definitionFile;
	}

	// =============== Methods ===============

	@Override
	public void enterImportDeclaration(GenTreeSrcParser.ImportDeclarationContext ctx)
	{
		final String packageName = getPackageName(ctx.packageName());
		final String typeName = ctx.typeName.getText();

		final ImportDecl import_ = ImportDecl.of(packageName, typeName);

		this.definitionFile.getImports().add(import_);
	}

	@Override
	public void enterTypeDeclaration(GenTreeSrcParser.TypeDeclarationContext ctx)
	{
		final boolean isAbstract = ctx.ABSTRACT() != null;
		final String packageName = getPackageName(ctx.packageName());
		final String className = ctx.className.getText();

		TypeDecl parent = this.currentDeclaration;
		final String fullPackageName = getPackageName(parent, packageName);

		this.currentDeclaration = TypeDecl.of(isAbstract, fullPackageName, className, parent, new ArrayList<>(),
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
		final String name = ctx.name.getText();
		final PropertyDecl property = PropertyDecl.of(name, this.type);
		this.currentDeclaration.getProperties().add(property);
	}

	// --------------- Types ---------------

	@Override
	public void exitNamedType(GenTreeSrcParser.NamedTypeContext ctx)
	{
		final String name = ctx.name.getText();
		this.type = NamedType.of(name);
	}

	@Override
	public void exitListType(GenTreeSrcParser.ListTypeContext ctx)
	{
		this.type = ListType.of(this.type);
	}

	@Override
	public void exitOptionalType(GenTreeSrcParser.OptionalTypeContext ctx)
	{
		this.type = OptionalType.of(this.type);
	}

	@Override
	public void exitTypeDeclaration(GenTreeSrcParser.TypeDeclarationContext ctx)
	{
		this.currentDeclaration = this.currentDeclaration.getSuperType();
	}
}
