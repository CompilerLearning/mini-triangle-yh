package visitor

import ast.*

class PrintingVisitor : Visitor<Int, Unit> {

    override fun accept(ast: AST) {
        if (ast is Program) {
            visitProgram(ast, 0)
        }
    }

    override fun visitProgram(program: Program, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "Program"))
        visitCommand(program.command, input + 1)
    }

    override fun visitAssignCommand(assignCommand: AssignCommand, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "AssignCommand"))

        if (assignCommand.vName is SimpleVName) {
            visitSimpleVName(assignCommand.vName, input + 1)
        }
        visitExpression(assignCommand.expression, input + 1)
    }

    override fun visitCallCommand(callCommand: CallCommand, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "CallCommand"))

        visitIdentifier(callCommand.identifier, input + 1)
        visitExpression(callCommand.expression, input + 1)
    }

    override fun visitSequentialCommand(sequentialCommand: SequentialCommand, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "SequentialCommand"))

        sequentialCommand.commands.forEach { command ->
            visitCommand(command, input + 1)
        }
    }

    override fun visitIfCommand(ifCommand: IfCommand, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "IfCommand"))

        visitExpression(ifCommand.expression, input + 1)
        visitCommand(ifCommand.trueCommand, input + 1)
        visitCommand(ifCommand.falseCommand, input + 1)
    }

    override fun visitWhileCommand(whileCommand: WhileCommand, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "WhileCommand"))

        visitExpression(whileCommand.expression, input + 1)
        visitCommand(whileCommand.command, input + 1)
    }

    override fun visitLetCommand(letCommand: LetCommand, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "LetCommand"))

        visitDeclaration(letCommand.declaration, input + 1)
        visitCommand(letCommand.command, input + 1)
    }

    override fun visitUnaryExpression(unaryExpression: UnaryExpression, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "UnaryExpression"))

        visitOperator(unaryExpression.operator, input + 1)
        visitExpression(unaryExpression.expression, input + 1)
    }

    override fun visitBinaryExpression(binaryExpression: BinaryExpression, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "BinaryExpression"))

        visitExpression(binaryExpression.leftExpression, input + 1)
        visitOperator(binaryExpression.operator, input + 1)
        visitExpression(binaryExpression.rightExpression, input + 1)
    }

    override fun visitIntegerExpression(integerExpression: IntegerExpression, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "IntegerExpression"))

        visitIntegerLiteral(integerExpression.integerLiteral, input + 1)
    }

    override fun visitVNameExpression(vNameExpression: VNameExpression, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "VNameExpression"))

        if (vNameExpression.vName is SimpleVName) {
            visitSimpleVName(vNameExpression.vName, input + 1)
        }
    }

    override fun visitConstDeclaration(constDeclaration: ConstDeclaration, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "ConstDeclaration"))

        visitIdentifier(constDeclaration.identifier, input + 1)
        visitExpression(constDeclaration.expression, input + 1)
    }

    override fun visitValDeclaration(valDeclaration: ValDeclaration, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "ValDeclaration"))

        visitIdentifier(valDeclaration.identifier, input + 1)
        if (valDeclaration.typeDenoter is SimpleTypeDenoter) {
            visitSimpleTypeDenoter(valDeclaration.typeDenoter, input + 1)
        }
    }

    override fun visitSequentialDeclaration(sequentialDeclaration: SequentialDeclaration, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "SequentialDeclaration"))

        sequentialDeclaration.declarations.forEach { declaration ->
            visitDeclaration(declaration, input + 1)
        }
    }

    override fun visitSimpleTypeDenoter(simpleTypeDenoter: SimpleTypeDenoter, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "SimpleTypeDenoter"))

        visitIdentifier(simpleTypeDenoter.identifier, input + 1)
    }

    override fun visitSimpleVName(simpleVName: SimpleVName, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "SimpleVName"))

        visitIdentifier(simpleVName.identifier, input + 1)
    }

    override fun visitIdentifier(identifier: Identifier, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "Identifier: ${identifier.spelling}"))
    }

    override fun visitIntegerLiteral(integerLiteral: IntegerLiteral, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "IntegerLiteral: ${integerLiteral.spelling}"))
    }

    override fun visitOperator(operator: Operator, input: Int?) {
        requireNotNull(input)
        println(buildContents(input, "Operator: ${operator.spelling}"))
    }

    private fun buildContents(depth: Int, nodeNane: String) = buildString {
        repeat(depth) {
            append("\t")
        }
        append(nodeNane)
    }

    private fun visitCommand(
        command: Command,
        depth: Int
    ) = when (command) {
        is AssignCommand -> visitAssignCommand(command, depth)
        is CallCommand -> visitCallCommand(command, depth)
        is IfCommand -> visitIfCommand(command, depth)
        is LetCommand -> visitLetCommand(command, depth)
        is SequentialCommand -> visitSequentialCommand(command, depth)
        is WhileCommand -> visitWhileCommand(command, depth)
    }

    private fun visitExpression(
        expression: Expression,
        depth: Int
    ) = when (expression) {
        is BinaryExpression -> visitBinaryExpression(expression, depth)
        is IntegerExpression -> visitIntegerExpression(expression, depth)
        is UnaryExpression -> visitUnaryExpression(expression, depth)
        is VNameExpression -> visitVNameExpression(expression, depth)
    }

    private fun visitDeclaration(
        declaration: Declaration,
        depth: Int
    ) = when (declaration) {
        is ConstDeclaration -> visitConstDeclaration(declaration, depth)
        is ValDeclaration -> visitValDeclaration(declaration, depth)
        is SequentialDeclaration -> visitSequentialDeclaration(declaration, depth)
    }
}