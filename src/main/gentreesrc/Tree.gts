import de.clashsoft.gentreesrc.tree.decl.Attributes

abstract de.clashsoft.gentreesrc.tree.Node {
	DefinitionFile(declarations: [TypeDecl])

	abstract decl.Decl {
		PropertyDecl(
			owner: TypeDecl
			attributes: Attributes
			name: String
			type: Type
		)

		TypeDecl(
			attributes: Attributes
			packageName: String
			className: String
			superType: TypeDecl
			properties: [PropertyDecl]
			readonly delegate constructorProperties: [PropertyDecl]
			readonly delegate storedProperties: [PropertyDecl]
			subTypes: [TypeDecl]
		)
	}

	abstract type.Type {
		NamedType(name: String, args: [Type]?)
		ArrayType(elementType: Type)
		ListType(elementType: Type)
		MapType(keyType: Type, valueType: Type)
		OptionalType(wrappedType: Type)
	}
}
