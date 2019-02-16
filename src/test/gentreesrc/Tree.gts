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

	Property(name: String, type: String, style: PropertyStyle)

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
