package de.clashsoft.gentreesrc.tree.decl;

public enum Modifier
{
	// types
	ABSTRACT(1),
	IMPORT(2),
	// properties
	DELEGATE(4),
	READONLY(8),
	NOCONSTRUCT(16),
	LAZY(32),
	;

	private final int flag;

	Modifier(int flag)
	{
		this.flag = flag;
	}

	public int getFlag()
	{
		return this.flag;
	}
}
