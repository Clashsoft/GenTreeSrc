package de.clashsoft.gentreesrc.tree;


public interface Import extends Node
{
	// =============== Static Methods ===============
	static Import of(String packageName, String typeName) { return new Impl(packageName, typeName); }
	// =============== Properties ===============
	String getPackageName();
	void setPackageName(String packageName);
	String getTypeName();
	void setTypeName(String typeName);
	// =============== Methods ===============
	<P, R> R accept(Import.Visitor<P, R> visitor, P par);
	// =============== Classes ===============
	class Impl extends Node.Impl implements Import
	{
		// =============== Fields ===============
		private String packageName;
		private String typeName;
		// =============== Constructors ===============
		public Impl()
		{
		}
		public Impl(String packageName, String typeName)
		{
			this.packageName = packageName;
			this.typeName = typeName;
		}
		// =============== Properties ===============
		@Override public String getPackageName() { return this.packageName; }
		@Override public void setPackageName(String packageName) { this.packageName = packageName; }
		@Override public String getTypeName() { return this.typeName; }
		@Override public void setTypeName(String typeName) { this.typeName = typeName; }
		// =============== Methods ===============
		@Override public <P, R> R accept(Import.Visitor<P, R> visitor, P par)
		{
			return visitor.visitImport(this, par);
		}

	}
	interface Visitor<P, R>
	{
		// =============== Methods ===============
		R visitImport(Import Import, P par);

	}

}
