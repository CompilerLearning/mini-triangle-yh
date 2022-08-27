package ast

abstract class Terminal(
    depth: Int,
    val spelling: String
) : AST(depth)

class Identifier(
    depth: Int,
    spelling: String
) : Terminal(depth, spelling) {

    override fun printNode() {
        println(buildContents("Identifier: $spelling"))
    }
}

class IntegerLiteral(
    depth: Int,
    spelling: String
) : Terminal(depth, spelling) {

    override fun printNode() {
        println(buildContents("IntegerLiteral: $spelling"))
    }
}

class Operator(
    depth: Int,
    spelling: String
) : Terminal(depth, spelling) {

    override fun printNode() {
        println(buildContents("Operator: $spelling"))
    }
}