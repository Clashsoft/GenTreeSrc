package de.clashsoft.gentreesrc.util;

import de.clashsoft.gentreesrc.tree.DefinitionFile;
import de.clashsoft.gentreesrc.tree.decl.ImportDecl;
import de.clashsoft.gentreesrc.tree.decl.PropertyDecl;
import de.clashsoft.gentreesrc.tree.decl.TypeDecl;
import de.clashsoft.gentreesrc.tree.type.*;

import java.util.Map;
import java.util.Set;

public class ImportHelper
{
	// --------------- Import Map ---------------

	public static void collectImportMap(DefinitionFile definitionFile, Map<String, String> importMap)
	{
		for (ImportDecl import_ : definitionFile.getImports())
		{
			importMap.put(import_.getTypeName(), import_.getPackageName());
		}

		for (TypeDecl decl : definitionFile.getDeclarations())
		{
			collectImportMap(decl, importMap);
		}
	}

	public static void collectImportMap(TypeDecl decl, Map<String, String> importMap)
	{
		importMap.put(decl.getClassName(), decl.getPackageName());

		for (TypeDecl subType : decl.getSubTypes())
		{
			collectImportMap(subType, importMap);
		}
	}

	// --------------- Imports ---------------

	public static void collectImports(Map<String, String> importMap, TypeDecl decl, Set<String> imports)
	{
		for (TypeDecl superType = decl.getSuperType(); superType != null; superType = superType.getSuperType())
		{
			addImport(decl, imports, superType);
		}

		collectImportsRecursively(importMap, decl, imports, decl);
	}

	private static void collectImportsRecursively(Map<String, String> importMap, TypeDecl decl,
		Set<String> imports, TypeDecl current)
	{
		addImport(decl, imports, current);

		for (PropertyDecl property : decl.getProperties())
		{
			addImport(importMap, decl, imports, property.getType());
		}

		for (TypeDecl subType : current.getSubTypes())
		{
			collectImportsRecursively(importMap, decl, imports, subType);
		}
	}

	private static void addImport(Map<String, String> importMap, TypeDecl decl, Set<String> imports, Type type)
	{
		type.accept(new Type.Visitor<Void, Void>()
		{
			@Override
			public Void visitType(Type type, Void par)
			{
				return null;
			}

			@Override
			public Void visitNamedType(NamedType namedType, Void par)
			{
				addImport(importMap, decl, imports, namedType.getName());
				return null;
			}

			@Override
			public Void visitListType(ListType listType, Void par)
			{
				imports.add("java.util.List");
				return listType.getElementType().accept(this, par);
			}

			@Override
			public Void visitOptionalType(OptionalType optionalType, Void par)
			{
				return optionalType.getWrappedType().accept(this, par);
			}

			@Override
			public Void visitMapType(MapType mapType, Void par)
			{
				imports.add("java.util.Map");
				mapType.getKeyType().accept(this, par);
				mapType.getValueType().accept(this, par);
				return null;
			}
		}, null);
	}

	private static void addImport(Map<String, String> importMap, TypeDecl decl, Set<String> imports,
		String className)
	{
		final String packageName = importMap.get(className);
		if (packageName != null && !packageName.equals(decl.getPackageName()))
		{
			imports.add(getFullName(packageName, className));
		}
	}

	private static void addImport(TypeDecl decl, Set<String> imports, TypeDecl target)
	{
		if (!packageEquals(decl, target))
		{
			imports.add(getFullName(target));
		}
	}

	// --------------- Declaration Helpers ---------------

	private static boolean packageEquals(TypeDecl a, TypeDecl b)
	{
		return a == b || a.getPackageName().equals(b.getPackageName());
	}

	private static String getFullName(TypeDecl target)
	{
		return getFullName(target.getPackageName(), target.getClassName());
	}

	private static String getFullName(String packageName, String className)
	{
		return packageName.isEmpty() ? className : packageName + "." + className;
	}
}
