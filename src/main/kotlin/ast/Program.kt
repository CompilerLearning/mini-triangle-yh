package ast

class Program(
    depth: Int,
    val command: Command
) : AST(depth) {

    override fun printNode() {
        println(buildContents("Program"))
        command.printNode()
    }
}