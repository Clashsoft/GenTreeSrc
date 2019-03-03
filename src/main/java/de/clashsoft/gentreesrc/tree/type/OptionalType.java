package de.clashsoft.gentreesrc.tree.type;

import de.clashsoft.gentreesrc.tree.Node;


public interface OptionalType extends Type
{
	// =============== Static Methods ===============
	static OptionalType of(Type wrappedType) { return new Impl(wrappedType); }

	// =============== Properties ===============
	Type getWrappedType();
	void setWrappedType(Type wrappedType);

	// =============== Methods ===============
	<P, R> R accept(OptionalType.Visitor<P, R> visitor, P par);

	// =============== Classes ===============
	class Impl extends Type.Impl implements OptionalType
	{
		// =============== Fields ===============
		private Type wrappedType;

		// =============== Constructors ===============
		public Impl() {}
		public Impl(Type wrappedType)
		{
			this.wrappedType = wrappedType;
		}

		// =============== Properties ===============
		@Override public Type getWrappedType() { return this.wrappedType; }
		@Override public void setWrappedType(Type wrappedType) { this.wrappedType = wrappedType; }

		// =============== Methods ===============
		@Override public <P, R> R accept(OptionalType.Visitor<P, R> visitor, P par) { return visitor.visitOptionalType(this, par); }
		@Override public <P, R> R accept(Type.Visitor<P, R> visitor, P par) { return visitor.visitOptionalType(this, par); }
		@Override public <P, R> R accept(Node.Visitor<P, R> visitor, P par) { return visitor.visitOptionalType(this, par); }

	}

	interface Visitor<P, R>
	{
		// =============== Methods ===============
		R visitOptionalType(OptionalType OptionalType, P par);
	}
}
