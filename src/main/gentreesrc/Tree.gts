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
			isAbstract: boolean
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
		NamedType(name: String)
		ListType(elementType: Type)
		OptionalType(wrappedType: Type)
	}
}
