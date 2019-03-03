grammar GenTreeSrc;

@header {
package de.clashsoft.gentreesrc.antlr;
}

// =============== Parser ===============

// --------------- Main File and Header ---------------

main:
	(importDeclaration ';'?)*
	(typeDeclaration ';'?)+;

importDeclaration:
	IMPORT packageName typeName=IDENTIFIER;

// --------------- Type Declarations ---------------

typeDeclaration:
	packageName className=IDENTIFIER propertyList? subtypeList?;

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
	;

namedType: name=IDENTIFIER;
listType: '[' elementType=type ']';
optionalType: nonOptionalType '?';

packageName: (IDENTIFIER '.')*;

// =============== Lexer ===============

IMPORT: 'import';

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9]*;

LINE_COMMENT: '//' .*? '\n' -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
COMMENT: LINE_COMMENT | BLOCK_COMMENT;

WS: [ \r\t\n] -> skip;
