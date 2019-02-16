package de.clashsoft.gentreesrc.util;

import de.clashsoft.gentreesrc.tree.PropertyStyle;
import de.clashsoft.gentreesrc.tree.TypeDeclaration;

import java.util.Set;

public class ImportHelper
{
	public static void collectImports(TypeDeclaration decl, Set<String> imports)
	{
		if (decl.getProperties().stream().anyMatch(p -> p.getStyle() == PropertyStyle.LIST))
		{
			imports.add("java.util.List");
		}

		if (decl.getSuperType() != null)
		{
			addImport(decl, imports, decl.getSuperType());
		}

		collectImportsRecursively(decl, imports, decl);
	}

	private static void collectImportsRecursively(TypeDeclaration decl, Set<String> imports, TypeDeclaration current)
	{
		addImport(decl, imports, current);

		for (TypeDeclaration subType : current.getSubTypes())
		{
			collectImportsRecursively(decl, imports, subType);
		}
	}

	private static void addImport(TypeDeclaration decl, Set<String> imports, TypeDeclaration target)
	{
		if (!packageEquals(decl, target))
		{
			imports.add(getFullName(target));
		}
	}

	private static boolean packageEquals(TypeDeclaration a, TypeDeclaration b)
	{
		return a == b || a.getPackageName().equals(b.getPackageName());
	}

	private static String getFullName(TypeDeclaration target)
	{
		return target.getPackageName().isEmpty() ?
			       target.getClassName() :
			       target.getPackageName() + "." + target.getClassName();
	}
}
