package de.clashsoft.gentreesrc.util;

import de.clashsoft.gentreesrc.tree.*;

import java.util.Map;
import java.util.Set;

public class ImportHelper
{
	// --------------- Import Map ---------------

	public static void collectImportMap(DefinitionFile definitionFile, Map<String, String> importMap)
	{
		for (Import import_ : definitionFile.getImports())
		{
			importMap.put(import_.getTypeName(), import_.getPackageName());
		}

		for (TypeDeclaration decl : definitionFile.getDeclarations())
		{
			collectImportMap(decl, importMap);
		}
	}

	public static void collectImportMap(TypeDeclaration decl, Map<String, String> importMap)
	{
		importMap.put(decl.getClassName(), decl.getPackageName());

		for (TypeDeclaration subType : decl.getSubTypes())
		{
			collectImportMap(subType, importMap);
		}
	}

	// --------------- Imports ---------------

	public static void collectImports(Map<String, String> importMap, TypeDeclaration decl, Set<String> imports)
	{
		if (decl.getProperties().stream().anyMatch(p -> p.getStyle() == PropertyStyle.LIST))
		{
			imports.add("java.util.List");
		}

		if (decl.getSuperType() != null)
		{
			addImport(decl, imports, decl.getSuperType());
		}

		collectImportsRecursively(importMap, decl, imports, decl);
	}

	private static void collectImportsRecursively(Map<String, String> importMap, TypeDeclaration decl,
		Set<String> imports, TypeDeclaration current)
	{
		addImport(decl, imports, current);

		for (Property property : decl.getProperties())
		{
			addImport(importMap, decl, imports, property.getType());
		}

		for (TypeDeclaration subType : current.getSubTypes())
		{
			collectImportsRecursively(importMap, decl, imports, subType);
		}
	}

	private static void addImport(Map<String, String> importMap, TypeDeclaration decl, Set<String> imports,
		String className)
	{
		final String packageName = importMap.get(className);
		if (packageName != null && !packageName.equals(decl.getPackageName()))
		{
			imports.add(getFullName(packageName, className));
		}
	}

	private static void addImport(TypeDeclaration decl, Set<String> imports, TypeDeclaration target)
	{
		if (!packageEquals(decl, target))
		{
			imports.add(getFullName(target));
		}
	}

	// --------------- Declaration Helpers ---------------

	private static boolean packageEquals(TypeDeclaration a, TypeDeclaration b)
	{
		return a == b || a.getPackageName().equals(b.getPackageName());
	}

	private static String getFullName(TypeDeclaration target)
	{
		return getFullName(target.getPackageName(), target.getClassName());
	}

	private static String getFullName(String packageName, String className)
	{
		return packageName.isEmpty() ? className : packageName + "." + className;
	}
}
