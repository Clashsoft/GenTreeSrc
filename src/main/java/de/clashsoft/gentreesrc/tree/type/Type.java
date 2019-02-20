package de.clashsoft.gentreesrc.tree.type;

import de.clashsoft.gentreesrc.tree.Node;


public interface Type extends Node
{
	// =============== Static Methods ===============
	static Type of() { return new Impl(); }

	// =============== Methods ===============
	<P, R> R accept(Type.Visitor<P, R> visitor, P par);

	// =============== Classes ===============
	class Impl extends Node.Impl implements Type
	{
		// =============== Methods ===============
		@Override public <P, R> R accept(Type.Visitor<P, R> visitor, P par) { return visitor.visitType(this, par); }
		@Override public <P, R> R accept(Node.Visitor<P, R> visitor, P par) { return visitor.visitType(this, par); }

	}

	interface Visitor<P, R> extends NamedType.Visitor<P, R>, ListType.Visitor<P, R>
	{
		// =============== Methods ===============
		R visitType(Type Type, P par);
	}
}
