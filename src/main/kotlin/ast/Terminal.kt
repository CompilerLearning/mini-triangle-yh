package ast

abstract class Terminal(
    val spelling: String
) : AST()

class Identifier(
    spelling: String
) : Terminal(spelling)

class IntegerLiteral(
    spelling: String
) : Terminal(spelling)

class Operator(
    spelling: String
) : Terminal(spelling)