package de.clashsoft.gentreesrc.tree;

import java.util.List;


public interface DefinitionFile extends Node
{
	// =============== Static Methods ===============
	static DefinitionFile of(List<Import> imports, List<TypeDeclaration> declarations) { return new Impl(imports, declarations); }

	// =============== Properties ===============
	List<Import> getImports();
	void setImports(List<Import> imports);
	List<TypeDeclaration> getDeclarations();
	void setDeclarations(List<TypeDeclaration> declarations);

	// =============== Methods ===============
	<P, R> R accept(DefinitionFile.Visitor<P, R> visitor, P par);

	// =============== Classes ===============
	class Impl extends Node.Impl implements DefinitionFile
	{
		// =============== Fields ===============
		private List<Import> imports;
		private List<TypeDeclaration> declarations;

		// =============== Constructors ===============
		public Impl() {}
		public Impl(List<Import> imports, List<TypeDeclaration> declarations)
		{
			this.imports = imports;
			this.declarations = declarations;
		}

		// =============== Properties ===============
		@Override public List<Import> getImports() { return this.imports; }
		@Override public void setImports(List<Import> imports) { this.imports = imports; }
		@Override public List<TypeDeclaration> getDeclarations() { return this.declarations; }
		@Override public void setDeclarations(List<TypeDeclaration> declarations) { this.declarations = declarations; }

		// =============== Methods ===============
		@Override public <P, R> R accept(DefinitionFile.Visitor<P, R> visitor, P par) { return visitor.visitDefinitionFile(this, par); }
		@Override public <P, R> R accept(Node.Visitor<P, R> visitor, P par) { return visitor.visitDefinitionFile(this, par); }

	}

	interface Visitor<P, R>
	{
		// =============== Methods ===============
		R visitDefinitionFile(DefinitionFile DefinitionFile, P par);
	}
}
