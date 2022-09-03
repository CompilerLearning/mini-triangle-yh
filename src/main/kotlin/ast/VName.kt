package ast

sealed class VName : AST

class SimpleVName(val identifier: Identifier) : VName()