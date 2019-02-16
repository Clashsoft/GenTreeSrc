package de.clashsoft.gentreesrc.tree;

import java.util.List;


public interface DefinitionFile extends Node
{
	// =============== Static Methods ===============
	static DefinitionFile of(List<TypeDeclaration> declarations) { return new Impl(declarations); }
	// =============== Properties ===============
	List<TypeDeclaration> getDeclarations();
	// =============== Methods ===============
	<P, R> R accept(DefinitionFile.Visitor<P, R> visitor, P par);
	// =============== Classes ===============
	class Impl extends Node.Impl implements DefinitionFile
	{
		// =============== Fields ===============
		private List<TypeDeclaration> declarations;
		// =============== Constructors ===============
		public Impl()
		{
		}
		public Impl(List<TypeDeclaration> declarations)
		{
			this.declarations = declarations;
		}
		// =============== Properties ===============
		@Override public List<TypeDeclaration> getDeclarations() { return this.declarations; }
		// =============== Methods ===============
		@Override public <P, R> R accept(DefinitionFile.Visitor<P, R> visitor, P par)
		{
			return visitor.visitDefinitionFile(this, par);
		}

	}
	interface Visitor<P, R>
	{
		// =============== Methods ===============
		R visitDefinitionFile(DefinitionFile DefinitionFile, P par);

	}

}
