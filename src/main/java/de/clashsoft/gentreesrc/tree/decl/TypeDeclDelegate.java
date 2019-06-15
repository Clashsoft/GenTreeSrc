package de.clashsoft.gentreesrc.tree.decl;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class TypeDeclDelegate
{
	private static List<PropertyDecl> filterProperties(TypeDecl impl, Predicate<PropertyDecl> predicate)
	{
		return impl.getProperties().stream().filter(predicate)
		           .collect(Collectors.toList());
	}

	static List<PropertyDecl> getConstructorProperties(TypeDecl typeDecl)
	{
		return filterProperties(typeDecl, it -> !it.getAttributes().isDelegate());
	}

	static List<PropertyDecl> getStoredProperties(TypeDecl typeDecl)
	{
		return filterProperties(typeDecl, it -> !it.getAttributes().isDelegate());
	}
}
