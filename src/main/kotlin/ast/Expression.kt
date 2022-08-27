package ast

abstract class Expression(
    depth: Int
) : AST(depth)

class UnaryExpression(
    depth: Int,
    val operator: Operator,
    val expression: Expression,
) : Expression(depth) {

    override fun printNode() {
        println(buildContents("UnaryExpression"))
        operator.printNode()
        expression.printNode()
    }
}

class BinaryExpression(
    depth: Int,
    val operator: Operator,
    val leftExpression: Expression,
    val rightExpression: Expression,
) : Expression(depth) {

    override fun printNode() {
        println(buildContents("BinaryExpression"))
        leftExpression.printNode()
        operator.printNode()
        rightExpression.printNode()
    }
}

class IntegerExpression(
    depth: Int,
    val integerExpression: IntegerLiteral
) : Expression(depth) {

    override fun printNode() {
        println(buildContents("IntegerExpression"))
        integerExpression.printNode()
    }
}

class VNameExpression(
    depth: Int,
    val vName: VName
) : Expression(depth) {

    override fun printNode() {
        println(buildContents("VNameExpression"))
        vName.printNode()
    }
}