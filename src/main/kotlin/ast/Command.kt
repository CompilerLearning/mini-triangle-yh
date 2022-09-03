package ast

sealed class Command(
    depth: Int
) : AST(depth)

class AssignCommand(
    depth: Int,
    val vName: VName,
    val expression: Expression
) : Command(depth) {

    override fun printNode() {
        println(buildContents("AssignCommand"))
        vName.printNode()
        expression.printNode()
    }
}

class CallCommand(
    depth: Int,
    val identifier: Identifier,
    val expression: Expression
) : Command(depth) {

    override fun printNode() {
        println(buildContents("CallCommand"))
        identifier.printNode()
        expression.printNode()
    }
}

class SequentialCommand(
    depth: Int,
    val commands: List<Command>
) : Command(depth) {

    override fun printNode() {
        println(buildContents("SequentialCommand"))
        commands.forEach {
            it.printNode()
        }
    }

    companion object {
        fun of(depth: Int, c: Command, o: SequentialCommand? = null): SequentialCommand {
            val commands = buildList {
                o?.commands?.let {
                    addAll(it)
                }
                add(c)
            }
            return SequentialCommand(depth, commands)
        }
    }
}

class IfCommand(
    depth: Int,
    val expression: Expression,
    val trueCommand: Command,
    val falseCommand: Command
) : Command(depth) {

    override fun printNode() {
        println(buildContents("IfCommand"))
        expression.printNode()
        trueCommand.printNode()
        falseCommand.printNode()
    }
}

class WhileCommand(
    depth: Int,
    val expression: Expression,
    val command: Command
) : Command(depth) {

    override fun printNode() {
        println(buildContents("WhileCommand"))
        expression.printNode()
        command.printNode()
    }
}


class LetCommand(
    depth: Int,
    val declaration: Declaration,
    val command: Command
) : Command(depth) {

    override fun printNode() {
        println(buildContents("LetCommand"))
        declaration.printNode()
        command.printNode()
    }
}
