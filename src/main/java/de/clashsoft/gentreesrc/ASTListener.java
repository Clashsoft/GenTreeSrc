package de.clashsoft.gentreesrc;

import de.clashsoft.gentreesrc.antlr.GenTreeSrcBaseListener;
import de.clashsoft.gentreesrc.antlr.GenTreeSrcParser;
import de.clashsoft.gentreesrc.tree.DefinitionFile;
import de.clashsoft.gentreesrc.tree.Property;
import de.clashsoft.gentreesrc.tree.PropertyStyle;
import de.clashsoft.gentreesrc.tree.TypeDeclaration;

import java.util.ArrayList;

public class ASTListener extends GenTreeSrcBaseListener
{
	// =============== Fields ===============

	private DefinitionFile definitionFile;

	private TypeDeclaration currentDeclaration;

	// =============== Constructors ===============

	public ASTListener(DefinitionFile definitionFile)
	{
		this.definitionFile = definitionFile;
	}

	// =============== Methods ===============

	@Override
	public void enterTypeDeclaration(GenTreeSrcParser.TypeDeclarationContext ctx)
	{
		final String packageText = ctx.packageName().getText();
		final String packageName = packageText.isEmpty() ?
			                           packageText :
			                           packageText.substring(0, packageText.length() - 1); // strip trailing period
		final String className = ctx.className.getText();

		TypeDeclaration parent = this.currentDeclaration;
		final String fullPackageName = getPackageName(parent, packageName);

		this.currentDeclaration = TypeDeclaration
			                          .of(fullPackageName, className, parent, new ArrayList<>(), new ArrayList<>());

		if (parent != null)
		{
			parent.getSubTypes().add(this.currentDeclaration);
		}
		else
		{
			this.definitionFile.getDeclarations().add(this.currentDeclaration);
		}
	}

	private static String getPackageName(TypeDeclaration parent, String child)
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

	@Override
	public void enterProperty(GenTreeSrcParser.PropertyContext ctx)
	{
		final String name = ctx.name.getText();
		final GenTreeSrcParser.PropertyTypeContext typeCtx = ctx.propertyType();

		if (typeCtx.elementType != null)
		{
			final String elementType = typeCtx.elementType.getText();
			final String listType = "List<" + elementType + ">";

			final Property property = Property.of(name, listType, PropertyStyle.LIST);
			this.currentDeclaration.getProperties().add(property);
		}
		else
		{
			final String type = typeCtx.typeName.getText();
			final Property property = Property.of(name, type, PropertyStyle.REGULAR);
			this.currentDeclaration.getProperties().add(property);
		}
	}

	@Override
	public void exitTypeDeclaration(GenTreeSrcParser.TypeDeclarationContext ctx)
	{
		this.currentDeclaration = this.currentDeclaration.getSuperType();
	}
}
