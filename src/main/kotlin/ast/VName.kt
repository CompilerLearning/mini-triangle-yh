package ast

abstract class VName : AST()

class SimpleVName(
    val identifier: Identifier
) : VName()