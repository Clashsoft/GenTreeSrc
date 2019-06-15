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
	propertyModifier* name=IDENTIFIER ':' type
	|
	propertyModifier* type name=IDENTIFIER
	;

propertyModifier: DELEGATE | READONLY;

// --------------- Types ---------------

type:
	primaryType typeSuffix*
	;

primaryType:
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

typeSuffix: optionalTypeSuffix | arrayTypeSuffix;
optionalTypeSuffix: '?';
arrayTypeSuffix: '[' ']';

packageName: (IDENTIFIER '.')*;

// =============== Lexer ===============

ABSTRACT: 'abstract';
IMPORT: 'import';
DELEGATE: 'delegate';
READONLY: 'readonly';

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9]*;

LINE_COMMENT: '//' .*? '\n' -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
COMMENT: LINE_COMMENT | BLOCK_COMMENT;

WS: [ \r\t\n] -> skip;
