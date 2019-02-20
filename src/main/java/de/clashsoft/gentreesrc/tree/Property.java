package de.clashsoft.gentreesrc.tree;

import de.clashsoft.gentreesrc.tree.type.Type;


public interface Property extends Node
{
	// =============== Static Methods ===============
	static Property of(String name, Type type) { return new Impl(name, type); }

	// =============== Properties ===============
	String getName();
	void setName(String name);
	Type getType();
	void setType(Type type);

	// =============== Methods ===============
	<P, R> R accept(Property.Visitor<P, R> visitor, P par);

	// =============== Classes ===============
	class Impl extends Node.Impl implements Property
	{
		// =============== Fields ===============
		private String name;
		private Type type;

		// =============== Constructors ===============
		public Impl() {}
		public Impl(String name, Type type)
		{
			this.name = name;
			this.type = type;
		}

		// =============== Properties ===============
		@Override public String getName() { return this.name; }
		@Override public void setName(String name) { this.name = name; }
		@Override public Type getType() { return this.type; }
		@Override public void setType(Type type) { this.type = type; }

		// =============== Methods ===============
		@Override public <P, R> R accept(Property.Visitor<P, R> visitor, P par) { return visitor.visitProperty(this, par); }
		@Override public <P, R> R accept(Node.Visitor<P, R> visitor, P par) { return visitor.visitProperty(this, par); }

	}

	interface Visitor<P, R>
	{
		// =============== Methods ===============
		R visitProperty(Property Property, P par);
	}
}
