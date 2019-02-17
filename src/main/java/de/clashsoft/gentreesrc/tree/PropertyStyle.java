package de.clashsoft.gentreesrc.tree;

public enum PropertyStyle
{
	REGULAR, LIST,
	// OPTIONAL,
	// VARARGS,

	;

	public boolean isList()
	{
		return this == LIST;
	}

	public boolean hasSetter()
	{
		return this == REGULAR;
	}
}
