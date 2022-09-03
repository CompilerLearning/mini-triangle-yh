package ast

sealed class Command : AST

class AssignCommand(
    val vName: VName,
    val expression: Expression
) : Command()

class CallCommand(
    val identifier: Identifier,
    val expression: Expression
) : Command()

class SequentialCommand(
    val commands: List<Command>
) : Command() {

    companion object {
        fun of(c: Command, o: SequentialCommand? = null): SequentialCommand {
            val commands = buildList {
                o?.commands?.let {
                    addAll(it)
                }
                add(c)
            }
            return SequentialCommand(commands)
        }
    }
}

class IfCommand(
    val expression: Expression,
    val trueCommand: Command,
    val falseCommand: Command
) : Command()

class WhileCommand(
    val expression: Expression,
    val command: Command
) : Command()

class LetCommand(
    val declaration: Declaration,
    val command: Command
) : Command()
