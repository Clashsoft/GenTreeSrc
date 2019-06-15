package de.clashsoft.gentreesrc.tree.decl;

public enum Modifier
{
	ABSTRACT(1), IMPORT(2), DELEGATE(4);

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
