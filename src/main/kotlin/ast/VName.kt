package ast

sealed class VName(
    depth: Int
) : AST(depth)

class SimpleVName(
    depth: Int,
    val identifier: Identifier
) : VName(depth) {

    override fun printNode() {
        println(buildContents("SimpleVName"))
        identifier.printNode()
    }
}