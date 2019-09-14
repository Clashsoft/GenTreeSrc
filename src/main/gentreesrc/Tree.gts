import de.clashsoft.gentreesrc.tree.decl.Attributes

abstract de.clashsoft.gentreesrc.tree.Node {
	DefinitionFile(declarations: [TypeDecl])

	abstract decl.Decl {
		TypeDecl(
			attributes: Attributes
			packageName: String
			className: String
			superType: TypeDecl
			members: [TypeMemberDecl]
			readonly delegate methods: [MethodDecl]
			readonly delegate properties: [PropertyDecl]
			readonly delegate constructorProperties: [PropertyDecl]
			readonly delegate storedProperties: [PropertyDecl]
			subTypes: [TypeDecl]
		)

		abstract TypeMemberDecl(owner: TypeDecl, attributes: Attributes, name: String, type: Type) {
			PropertyDecl(
				owner: TypeDecl
				attributes: Attributes
				name: String
				type: Type
			)

			MethodDecl(
				owner: TypeDecl
				attributes: Attributes
				name: String
				parameters: [ParameterDecl]
				type: Type
			)
		}

		ParameterDecl(
			owner: MethodDecl
			attributes: Attributes
			name: String
			type: Type
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
