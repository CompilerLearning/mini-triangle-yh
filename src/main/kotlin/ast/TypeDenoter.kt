package ast

abstract class TypeDenoter(
    depth: Int
) : AST(depth)

class SimpleTypeDenoter(
    depth: Int,
    val identifier: Identifier
) : TypeDenoter(depth) {

    override fun printNode() {
        println(buildContents("SimpleTypeDenoter"))
        identifier.printNode()
    }
}

