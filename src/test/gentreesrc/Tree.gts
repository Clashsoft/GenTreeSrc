de.clashsoft.gentreesrc.tree.Node {
	Import(packageName: String, typeName: String)
	/*```
	import <packageName>.<typeName>;
	```*/

	DefinitionFile(imports: [Import], declarations: [TypeDeclaration])
	/*```
	<imports\n...>

	<declarations\n...>
	```*/

	Property(name: String, type: Type)

	type.Type {
		NamedType(name: String)
		ListType(elementType: Type)
		OptionalType(wrappedType: Type)
	}

	TypeDeclaration(
		packageName: String
		className: String
		superType: TypeDeclaration
		properties: [Property]
		subTypes: [TypeDeclaration]
	)
	/*```
	<packageName>.<className>(<properties, ...>) {
		<subTypes\n...>
	}
	```*/
}
