grammar GenTreeSrc;

@header {
package de.clashsoft.gentreesrc.antlr;
}

// =============== Parser ===============

main:
	(typeDeclaration ';'?)+;

typeDeclaration:
	packageName className=IDENTIFIER propertyList? subtypeList?;

subtypeList:
	'{' (typeDeclaration ';'?)* '}';

propertyList:
	'(' (property ','?)* ')';

property:
	name=IDENTIFIER ':' propertyType;

propertyType:
	typeName=IDENTIFIER
	|
	'[' elementType=IDENTIFIER ']'
	/*
	|
	optionalType=IDENTIFIER '?'
	|
	'final' immutableType=IDENTIFIER
	|
	varargsType=IDENTIFIER '...'
	*/
	;

packageName: (IDENTIFIER '.')*;

// =============== Lexer ===============

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9]*;
LINE_COMMENT: '//' .*? '\n' -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
COMMENT: LINE_COMMENT | BLOCK_COMMENT;
WS: [ \r\t\n] -> skip;
