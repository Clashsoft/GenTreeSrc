org.test.properties.Person(
	readonly id: long
	firstName: String
	lastName: String
	delegate fullName: String
	readonly delegate idString: String
	noconstruct tag: String
)

// #31 Duplicate no-args constructor if all properties are noconstruct
org.test.properties.NoConstruct(noconstruct i: int)
