package de.clashsoft.gentreesrc.tree;


public interface Property extends Node
{
	// =============== Static Methods ===============
	static Property of(String name, String type, PropertyStyle style) { return new Impl(name, type, style); }
	// =============== Properties ===============
	String getName();
	void setName(String name);
	String getType();
	void setType(String type);
	PropertyStyle getStyle();
	void setStyle(PropertyStyle style);
	// =============== Methods ===============
	<P, R> R accept(Property.Visitor<P, R> visitor, P par);
	// =============== Classes ===============
	class Impl extends Node.Impl implements Property
	{
		// =============== Fields ===============
		private String name;
		private String type;
		private PropertyStyle style;
		// =============== Constructors ===============
		public Impl()
		{
		}
		public Impl(String name, String type, PropertyStyle style)
		{
			this.name = name;
			this.type = type;
			this.style = style;
		}
		// =============== Properties ===============
		@Override public String getName() { return this.name; }
		@Override public void setName(String name) { this.name = name; }
		@Override public String getType() { return this.type; }
		@Override public void setType(String type) { this.type = type; }
		@Override public PropertyStyle getStyle() { return this.style; }
		@Override public void setStyle(PropertyStyle style) { this.style = style; }
		// =============== Methods ===============
		@Override public <P, R> R accept(Property.Visitor<P, R> visitor, P par)
		{
			return visitor.visitProperty(this, par);
		}

	}
	interface Visitor<P, R>
	{
		// =============== Methods ===============
		R visitProperty(Property Property, P par);

	}

}