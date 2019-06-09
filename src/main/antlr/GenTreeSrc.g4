grammar GenTreeSrc;

// =============== Parser ===============

// --------------- Main File and Header ---------------

main: (typeDeclaration ';'?)+;

// --------------- Type Declarations ---------------

typeDeclaration:
	typeModifier* packageName className=IDENTIFIER propertyList? subtypeList?;

typeModifier: ABSTRACT | IMPORT;

subtypeList:
	'{' (typeDeclaration ';'?)* '}';

// --------------- Properties ---------------

propertyList:
	'(' (property ','?)* ')';

property:
	name=IDENTIFIER ':' type
	|
	type name=IDENTIFIER
	;

// --------------- Types ---------------

type:
	nonOptionalType
	|
	optionalType
	;

nonOptionalType:
	namedType
	|
	listType
	|
	mapType
	;

namedType: name=IDENTIFIER genericArguments?;
genericArguments: '<' (type ','?)* '>';
listType: '[' elementType=type ']';
mapType: '[' keyType=type ':' valueType=type ']';
optionalType: nonOptionalType '?';

packageName: (IDENTIFIER '.')*;

// =============== Lexer ===============

ABSTRACT: 'abstract';
IMPORT: 'import';

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9]*;

LINE_COMMENT: '//' .*? '\n' -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
COMMENT: LINE_COMMENT | BLOCK_COMMENT;

WS: [ \r\t\n] -> skip;
