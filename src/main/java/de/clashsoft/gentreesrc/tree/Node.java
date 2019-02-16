package de.clashsoft.gentreesrc.tree;


public interface Node
{
	// =============== Static Methods ===============
	static Node of() { return new Impl(); }
	// =============== Methods ===============
	<P, R> R accept(Node.Visitor<P, R> visitor, P par);
	// =============== Classes ===============
	class Impl implements Node
	{
		// =============== Methods ===============
		@Override public <P, R> R accept(Node.Visitor<P, R> visitor, P par)
		{
			return visitor.visitNode(this, par);
		}

	}
	interface Visitor<P, R> extends DefinitionFile.Visitor<P, R>, Property.Visitor<P, R>, TypeDeclaration.Visitor<P, R>
	{
		// =============== Methods ===============
		R visitNode(Node Node, P par);
			@Override R visitDefinitionFile(DefinitionFile DefinitionFile, P par);
			@Override R visitProperty(Property Property, P par);
			@Override R visitTypeDeclaration(TypeDeclaration TypeDeclaration, P par);

	}

}
