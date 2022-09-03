package visitor

import ast.*

interface Visitor<I, O> {

    fun accept(ast: AST)

    /* Program */
    fun visitProgram(program: Program, input: I? = null): O?

    /* Command */
    fun visitAssignCommand(assignCommand: AssignCommand, input: I? = null): O
    fun visitCallCommand(callCommand: CallCommand, input: I? = null): O
    fun visitSequentialCommand(sequentialCommand: SequentialCommand, input: I? = null): O
    fun visitIfCommand(ifCommand: IfCommand, input: I? = null): O
    fun visitWhileCommand(whileCommand: WhileCommand, input: I? = null): O
    fun visitLetCommand(letCommand: LetCommand, input: I? = null): O

    /* Expression */
    fun visitUnaryExpression(unaryExpression: UnaryExpression, input: I? = null): O
    fun visitBinaryExpression(binaryExpression: BinaryExpression, input: I? = null): O
    fun visitIntegerExpression(integerExpression: IntegerExpression, input: I? = null): O
    fun visitVNameExpression(vNameExpression: VNameExpression, input: I? = null): O

    /* Declaration */
    fun visitConstDeclaration(constDeclaration: ConstDeclaration, input: I? = null): O
    fun visitValDeclaration(valDeclaration: ValDeclaration, input: I? = null): O
    fun visitSequentialDeclaration(sequentialDeclaration: SequentialDeclaration, input: I? = null): O

    /* TypeDenoter */
    fun visitSimpleTypeDenoter(simpleTypeDenoter: SimpleTypeDenoter, input: I? = null): O

    /* VName */
    fun visitSimpleVName(simpleVName: SimpleVName, input: I? = null): O

    /* Terminal */
    fun visitIdentifier(identifier: Identifier, input: I? = null): O
    fun visitIntegerLiteral(integerLiteral: IntegerLiteral, input: I? = null): O
    fun visitOperator(operator: Operator, input: I? = null): O
}