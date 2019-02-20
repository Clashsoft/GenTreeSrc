package de.clashsoft.gentreesrc.tree.type;

import de.clashsoft.gentreesrc.tree.Node;


public interface ListType extends Type
{
	// =============== Static Methods ===============
	static ListType of(Type elementType) { return new Impl(elementType); }

	// =============== Properties ===============
	Type getElementType();
	void setElementType(Type elementType);

	// =============== Methods ===============
	<P, R> R accept(ListType.Visitor<P, R> visitor, P par);

	// =============== Classes ===============
	class Impl extends Type.Impl implements ListType
	{
		// =============== Fields ===============
		private Type elementType;

		// =============== Constructors ===============
		public Impl() {}
		public Impl(Type elementType)
		{
			this.elementType = elementType;
		}

		// =============== Properties ===============
		@Override public Type getElementType() { return this.elementType; }
		@Override public void setElementType(Type elementType) { this.elementType = elementType; }

		// =============== Methods ===============
		@Override public <P, R> R accept(ListType.Visitor<P, R> visitor, P par) { return visitor.visitListType(this, par); }
		@Override public <P, R> R accept(Type.Visitor<P, R> visitor, P par) { return visitor.visitListType(this, par); }
		@Override public <P, R> R accept(Node.Visitor<P, R> visitor, P par) { return visitor.visitListType(this, par); }

	}

	interface Visitor<P, R>
	{
		// =============== Methods ===============
		R visitListType(ListType ListType, P par);
	}
}
