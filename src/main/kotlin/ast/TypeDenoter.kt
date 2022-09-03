package ast

sealed class TypeDenoter : AST

class SimpleTypeDenoter(val identifier: Identifier) : TypeDenoter()

