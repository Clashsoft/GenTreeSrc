package de.clashsoft.gentreesrc.tree.type;

import de.clashsoft.gentreesrc.tree.Node;


public interface NamedType extends Type
{
	// =============== Static Methods ===============
	static NamedType of(String name) { return new Impl(name); }

	// =============== Properties ===============
	String getName();
	void setName(String name);

	// =============== Methods ===============
	<P, R> R accept(NamedType.Visitor<P, R> visitor, P par);

	// =============== Classes ===============
	class Impl extends Type.Impl implements NamedType
	{
		// =============== Fields ===============
		private String name;

		// =============== Constructors ===============
		public Impl() {}
		public Impl(String name)
		{
			this.name = name;
		}

		// =============== Properties ===============
		@Override public String getName() { return this.name; }
		@Override public void setName(String name) { this.name = name; }

		// =============== Methods ===============
		@Override public <P, R> R accept(NamedType.Visitor<P, R> visitor, P par) { return visitor.visitNamedType(this, par); }
		@Override public <P, R> R accept(Type.Visitor<P, R> visitor, P par) { return visitor.visitNamedType(this, par); }
		@Override public <P, R> R accept(Node.Visitor<P, R> visitor, P par) { return visitor.visitNamedType(this, par); }

	}

	interface Visitor<P, R>
	{
		// =============== Methods ===============
		R visitNamedType(NamedType NamedType, P par);
	}
}
