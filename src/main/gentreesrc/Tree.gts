import de.clashsoft.gentreesrc.tree.decl.Attributes

de.clashsoft.gentreesrc.tree.Node {
	DefinitionFile(declarations: [TypeDecl])
	/*```
	<imports\n...>

	<declarations\n...>
	```*/

	decl.Decl {
		PropertyDecl(name: String, type: Type)

		TypeDecl(
			attributes: Attributes
			packageName: String
			className: String
			superType: TypeDecl
			properties: [PropertyDecl]
			subTypes: [TypeDecl]
		)
		/*```
		<packageName>.<className>(<properties, ...>) {
			<subTypes\n...>
		}
		```*/
	}

	type.Type {
		NamedType(name: String, args: [Type]?)
		ListType(elementType: Type)
		MapType(keyType: Type, valueType: Type)
		OptionalType(wrappedType: Type)
	}
}
