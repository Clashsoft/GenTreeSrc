package de.clashsoft.gentreesrc.tree;

import de.clashsoft.gentreesrc.tree.type.ListType;
import de.clashsoft.gentreesrc.tree.type.NamedType;
import de.clashsoft.gentreesrc.tree.type.OptionalType;
import de.clashsoft.gentreesrc.tree.type.Type;


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
		@Override public <P, R> R accept(Node.Visitor<P, R> visitor, P par) { return visitor.visitNode(this, par); }

	}

	interface Visitor<P, R> extends Import.Visitor<P, R>, DefinitionFile.Visitor<P, R>, Property.Visitor<P, R>, Type.Visitor<P, R>, TypeDeclaration.Visitor<P, R>
	{
		// =============== Methods ===============
		R visitNode(Node Node, P par);
	}
}
