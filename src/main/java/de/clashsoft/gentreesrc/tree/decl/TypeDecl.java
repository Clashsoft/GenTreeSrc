package de.clashsoft.gentreesrc.tree.decl;

import de.clashsoft.gentreesrc.tree.Node;
import java.util.List;


public interface TypeDecl extends Decl
{
	// =============== Static Methods ===============
	static TypeDecl of(Attributes attributes, String packageName, String className, TypeDecl superType, List<PropertyDecl> properties, List<TypeDecl> subTypes) { return new Impl(attributes, packageName, className, superType, properties, subTypes); }
	// =============== Properties ===============
	Attributes getAttributes();
	void setAttributes(Attributes attributes);
	String getPackageName();
	void setPackageName(String packageName);
	String getClassName();
	void setClassName(String className);
	TypeDecl getSuperType();
	void setSuperType(TypeDecl superType);
	List<PropertyDecl> getProperties();
	void setProperties(List<PropertyDecl> properties);
	List<PropertyDecl> getConstructorProperties();
	List<PropertyDecl> getStoredProperties();
	List<TypeDecl> getSubTypes();
	void setSubTypes(List<TypeDecl> subTypes);

	// =============== Methods ===============
	<P, R> R accept(TypeDecl.Visitor<P, R> visitor, P par);
	// =============== Classes ===============
	class Impl implements TypeDecl
	{
		// =============== Fields ===============
		private Attributes attributes;
		private String packageName;
		private String className;
		private TypeDecl superType;
		private List<PropertyDecl> properties;
		private List<TypeDecl> subTypes;

		// =============== Constructors ===============
		public Impl() {}
		public Impl(Attributes attributes, String packageName, String className, TypeDecl superType, List<PropertyDecl> properties, List<TypeDecl> subTypes)
		{
			this.attributes = attributes;
			this.packageName = packageName;
			this.className = className;
			this.superType = superType;
			this.properties = properties;
			this.subTypes = subTypes;
		}

		// =============== Properties ===============
		@Override public Attributes getAttributes() { return this.attributes; }
		@Override public void setAttributes(Attributes attributes) { this.attributes = attributes; }
		@Override public String getPackageName() { return this.packageName; }
		@Override public void setPackageName(String packageName) { this.packageName = packageName; }
		@Override public String getClassName() { return this.className; }
		@Override public void setClassName(String className) { this.className = className; }
		@Override public TypeDecl getSuperType() { return this.superType; }
		@Override public void setSuperType(TypeDecl superType) { this.superType = superType; }
		@Override public List<PropertyDecl> getProperties() { return this.properties; }
		@Override public void setProperties(List<PropertyDecl> properties) { this.properties = properties; }
		@Override public List<PropertyDecl> getConstructorProperties() { return TypeDeclDelegate.getConstructorProperties(this); }
		@Override public List<PropertyDecl> getStoredProperties() { return TypeDeclDelegate.getStoredProperties(this); }
		@Override public List<TypeDecl> getSubTypes() { return this.subTypes; }
		@Override public void setSubTypes(List<TypeDecl> subTypes) { this.subTypes = subTypes; }

		// =============== Methods ===============
		@Override public <P, R> R accept(TypeDecl.Visitor<P, R> visitor, P par) { return visitor.visit(this, par); }
		@Override public <P, R> R accept(Decl.Visitor<P, R> visitor, P par) { return visitor.visit(this, par); }
		@Override public <P, R> R accept(Node.Visitor<P, R> visitor, P par) { return visitor.visit(this, par); }

	}
	interface Visitor<P, R>
	{
		// =============== Methods ===============
		R visit(TypeDecl typeDecl, P par);
	}
}
