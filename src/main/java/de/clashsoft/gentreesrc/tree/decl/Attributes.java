package de.clashsoft.gentreesrc.tree.decl;

import java.util.EnumSet;
import java.util.Set;

public class Attributes
{
	private EnumSet<Modifier> modifiers;

	public Set<Modifier> getModifiers()
	{
		if (this.modifiers == null)
		{
			return this.modifiers = EnumSet.noneOf(Modifier.class);
		}
		return this.modifiers;
	}

	public boolean hasModifier(Modifier modifier)
	{
		return this.modifiers != null && this.modifiers.contains(modifier);
	}

	public void add(Modifier modifier)
	{
		this.getModifiers().add(modifier);
	}

	public void removeModifier(Modifier modifier)
	{
		if (this.modifiers != null)
		{
			this.modifiers.remove(modifier);
		}
	}

	// @formatter:off
	public boolean isAbstract() { return this.hasModifier(Modifier.ABSTRACT); }
	public boolean isImport() { return this.hasModifier(Modifier.IMPORT); }
	public boolean isDelegate() { return this.hasModifier(Modifier.DELEGATE); }
	// @formatter:on
}
