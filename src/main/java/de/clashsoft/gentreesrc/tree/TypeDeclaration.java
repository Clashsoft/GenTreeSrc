package de.clashsoft.gentreesrc.tree;

import java.util.List;


public interface TypeDeclaration extends Node
{
	// =============== Static Methods ===============
	static TypeDeclaration of(String packageName, String className, TypeDeclaration superType, List<Property> properties, List<TypeDeclaration> subTypes) { return new Impl(packageName, className, superType, properties, subTypes); }
	// =============== Properties ===============
	String getPackageName();
	void setPackageName(String packageName);
	String getClassName();
	void setClassName(String className);
	TypeDeclaration getSuperType();
	void setSuperType(TypeDeclaration superType);
	List<Property> getProperties();
	List<TypeDeclaration> getSubTypes();
	// =============== Methods ===============
	<P, R> R accept(TypeDeclaration.Visitor<P, R> visitor, P par);
	// =============== Classes ===============
	class Impl extends Node.Impl implements TypeDeclaration
	{
		// =============== Fields ===============
		private String packageName;
		private String className;
		private TypeDeclaration superType;
		private List<Property> properties;
		private List<TypeDeclaration> subTypes;

		// =============== Constructors ===============
		public Impl() {}
		public Impl(String packageName, String className, TypeDeclaration superType, List<Property> properties, List<TypeDeclaration> subTypes)
		{
			this.packageName = packageName;
			this.className = className;
			this.superType = superType;
			this.properties = properties;
			this.subTypes = subTypes;
		}
		// =============== Properties ===============
		@Override public String getPackageName() { return this.packageName; }
		@Override public void setPackageName(String packageName) { this.packageName = packageName; }
		@Override public String getClassName() { return this.className; }
		@Override public void setClassName(String className) { this.className = className; }
		@Override public TypeDeclaration getSuperType() { return this.superType; }
		@Override public void setSuperType(TypeDeclaration superType) { this.superType = superType; }
		@Override public List<Property> getProperties() { return this.properties; }
		@Override public List<TypeDeclaration> getSubTypes() { return this.subTypes; }
		// =============== Methods ===============
		@Override public <P, R> R accept(TypeDeclaration.Visitor<P, R> visitor, P par)
		{
			return visitor.visitTypeDeclaration(this, par);
		}
	}
	interface Visitor<P, R>
	{
		// =============== Methods ===============
		R visitTypeDeclaration(TypeDeclaration TypeDeclaration, P par);
	}
}
