package ast

abstract class Declaration : AST()

class ConstDeclaration(
    val identifier: Identifier,
    val expression: Expression
) : Declaration()

class ValDeclaration(
    val identifier: Identifier,
    val typeDenoter: TypeDenoter
): Declaration()

class SequentialDeclaration(
    val declaration1: Declaration,
    val declaration2: Declaration,
): Declaration()