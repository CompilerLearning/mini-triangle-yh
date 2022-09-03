package ast

sealed class Expression : AST

class UnaryExpression(
    val operator: Operator,
    val expression: Expression,
) : Expression()

class BinaryExpression(
    val operator: Operator,
    val leftExpression: Expression,
    val rightExpression: Expression,
) : Expression()

class IntegerExpression(
    val integerLiteral: IntegerLiteral
) : Expression()

class VNameExpression(
    val vName: VName
) : Expression()