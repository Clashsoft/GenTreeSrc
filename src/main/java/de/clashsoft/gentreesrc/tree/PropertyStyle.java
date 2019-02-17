package de.clashsoft.gentreesrc.tree;

public enum PropertyStyle
{
	REGULAR, LIST,
	// OPTIONAL,
	// VARARGS,

	;

	public boolean hasSetter()
	{
		return this == REGULAR;
	}
}
