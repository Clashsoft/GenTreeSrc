import de.clashsoft.gentreesrc.tree.decl.Attributes

de.clashsoft.gentreesrc.tree.Node {
	DefinitionFile(imports: [ImportDecl], declarations: [TypeDecl])
	/*```
	<imports\n...>

	<declarations\n...>
	```*/

	decl.Decl {
		ImportDecl(packageName: String, typeName: String)
		/*```
		import <packageName>.<typeName>;
		```*/

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
