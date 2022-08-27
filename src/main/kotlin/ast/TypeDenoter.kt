package ast

abstract class TypeDenoter : AST()

class SimpleTypeDenoter(
    val identifier: Identifier
) : TypeDenoter()

