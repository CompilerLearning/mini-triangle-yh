package ast

sealed class Declaration(
    depth: Int
) : AST(depth)

class ConstDeclaration(
    depth: Int,
    val identifier: Identifier,
    val expression: Expression
) : Declaration(depth) {

    override fun printNode() {
        println(buildContents("ConstDeclaration"))
        identifier.printNode()
        expression.printNode()
    }
}

class ValDeclaration(
    depth: Int,
    val identifier: Identifier,
    val typeDenoter: TypeDenoter
) : Declaration(depth) {

    override fun printNode() {
        println(buildContents("ValDeclaration"))
        identifier.printNode()
        typeDenoter.printNode()
    }
}

class SequentialDeclaration(
    depth: Int,
    val declarations: List<Declaration>
) : Declaration(depth) {

    override fun printNode() {
        println(buildContents("SequentialDeclaration"))
        declarations.forEach {
            it.printNode()
        }
    }

    companion object {
        fun of(depth: Int, c: Declaration, o: SequentialDeclaration? = null): SequentialDeclaration {
            val declarations = buildList {
                o?.declarations?.let {
                    addAll(it)
                }
                add(c)
            }
            return SequentialDeclaration(depth, declarations)
        }
    }
}