package de.clashsoft.gentreesrc.tree;

import java.util.List;

public interface DefinitionFile extends Node
{
	// =============== Static Methods ===============

	static DefinitionFile of(List<TypeDeclaration> declarations)
	{
		return new Impl(declarations);
	}

	// =============== Properties ===============

	List<TypeDeclaration> getDeclarations();

	// =============== Classes ===============

	class Impl extends Node.Impl implements DefinitionFile
	{
		// =============== Fields ===============

		private List<TypeDeclaration> declarations;

		// =============== Constructors ===============

		public Impl(List<TypeDeclaration> declarations)
		{
			this.declarations = declarations;
		}

		// =============== Properties ===============

		@Override
		public List<TypeDeclaration> getDeclarations()
		{
			return this.declarations;
		}

		// =============== Methods ===============

		@Override
		public String toString()
		{
			return "DefinitionFile(declarations: " + this.declarations + ")";
		}
	}
}
