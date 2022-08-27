package ast

abstract class Command : AST()

class AssignCommand(
    val vName: VName,
    val expression: Expression
) : Command()

class CallCommand(
    val identifier: Identifier,
    val expression: Expression
) : Command()

class SequentialCommand(
    val command1: Command,
    val command2: Command
) : Command()

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
