package de.clashsoft.gentreesrc.tree.decl;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class TypeDeclDelegate
{
	private static List<PropertyDecl> filterProperties(TypeDecl impl, Predicate<PropertyDecl> predicate)
	{
		return impl.getProperties().stream().filter(predicate).collect(Collectors.toList());
	}

	static List<PropertyDecl> getConstructorProperties(TypeDecl typeDecl)
	{
		return filterProperties(typeDecl, it -> {
			final Attributes attributes = it.getAttributes();
			return !attributes.isDelegate() && !attributes.isNoconstruct();
		});
	}

	static List<PropertyDecl> getStoredProperties(TypeDecl typeDecl)
	{
		return filterProperties(typeDecl, it -> !it.getAttributes().isDelegate());
	}

	public static List<MethodDecl> getMethods(TypeDecl typeDecl)
	{
		return typeDecl.getMembers().stream().filter(m -> m instanceof MethodDecl).map(m -> (MethodDecl) m)
		               .collect(Collectors.toList());
	}

	public static List<PropertyDecl> getProperties(TypeDecl typeDecl)
	{
		return typeDecl.getMembers().stream().filter(m -> m instanceof PropertyDecl).map(m -> (PropertyDecl) m)
		               .collect(Collectors.toList());
	}
}
