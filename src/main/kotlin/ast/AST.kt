package ast

abstract class AST(
    var depth: Int
) {
    fun buildContents(nodeNane: String) = buildString {
        repeat(depth) {
            append("\t")
        }
        append(nodeNane)
    }

    abstract fun printNode()
}