package parser

import Characters
import ErrorHelper.throwError
import ReservedWords
import ast.*
import scanner.Scanner
import scanner.Token

class Parser(
    private val scanner: Scanner
) {

    private var currentToken: Token? = scanner.scan()

    fun parse() {
        parseProgram()
        if (currentToken?.kind != Token.Kind.EOT) {
            throwError()
        }
    }

    private fun accept(expected: Token) {
        if (currentToken == expected) {
            currentToken = scanner.scan()
        } else {
            throwError()
        }
    }

    private fun acceptIt() {
        currentToken = scanner.scan()
    }

    private fun parseProgram(): Program {
        val command = parseSingleCommand()
        return Program(command)
    }

    private fun parseCommand(): Command {
        val command = parseSingleCommand()
        var sequentialCommand = SequentialCommand.of(command)
        while (currentToken?.kind == Token.Kind.SEMICOLON) {
            acceptIt()
            sequentialCommand = SequentialCommand.of(parseSingleCommand(), sequentialCommand)
        }
        return sequentialCommand
    }

    private fun parseSingleCommand(): Command {
        return when (currentToken?.kind) {
            Token.Kind.IF -> {
                acceptIt()
                val expression = parseExpression()
                accept(Token(Token.Kind.THEN, ReservedWords.THEN))
                val trueCommand = parseSingleCommand()
                accept(Token(Token.Kind.ELSE, ReservedWords.ELSE))
                val falseCommand = parseSingleCommand()
                IfCommand(expression, trueCommand, falseCommand)
            }
            Token.Kind.WHILE -> {
                acceptIt()
                val expression = parseExpression()
                accept(Token(Token.Kind.DO, ReservedWords.DO))
                val command = parseSingleCommand()
                WhileCommand(expression, command)
            }
            Token.Kind.LET -> {
                acceptIt()
                val declaration = parseDeclaration()
                accept(Token(Token.Kind.IN, ReservedWords.IN))
                val command = parseSingleCommand()
                LetCommand(declaration, command)
            }
            Token.Kind.BEGIN -> {
                acceptIt()
                val command = parseCommand()
                accept(Token(Token.Kind.END, ReservedWords.END))
                command
            }
            Token.Kind.IDENTIFIER -> {
                val identifier = parseIdentifier()
                when (currentToken?.kind) {
                    Token.Kind.BECOMES -> {
                        acceptIt()
                        val expression = parseExpression()
                        AssignCommand(SimpleVName(identifier), expression)
                    }
                    Token.Kind.LPAREN -> {
                        acceptIt()
                        val expression = parseExpression()
                        accept(Token(Token.Kind.RPAREN, Characters.RIGHT_PAREN.toString()))
                        CallCommand(identifier, expression)
                    }
                    else -> {
                        throwError()
                    }
                }
            }
            else -> {
                throwError()
            }
        }
    }

    private fun parseExpression(): Expression {
        var leftExpression: Expression = parsePrimaryExpression()
        while (currentToken?.kind == Token.Kind.OPERATOR) {
            val operator = parseOperator()
            val rightExpression = parsePrimaryExpression()
            leftExpression = BinaryExpression(operator, leftExpression, rightExpression)
        }
        return leftExpression
    }

    private fun parsePrimaryExpression(): Expression {
        return when (currentToken?.kind) {
            Token.Kind.INT_LITERAL -> {
                val literal = parseIntegerLiteral()
                IntegerExpression(literal)
            }
            Token.Kind.IDENTIFIER -> {
                val vName = SimpleVName(parseIdentifier())
                VNameExpression(vName)
            }
            Token.Kind.OPERATOR -> {
                val operator = parseOperator()
                val expression = parsePrimaryExpression()
                UnaryExpression(operator, expression)
            }
            Token.Kind.LPAREN -> {
                acceptIt()
                val expression = parseExpression()
                accept(Token(Token.Kind.RPAREN, Characters.RIGHT_PAREN.toString()))
                expression
            }
            else -> {
                throwError()
            }
        }
    }

    private fun parseDeclaration(): Declaration {
        val declaration = parseSingleDeclaration()
        var sequentialDeclaration = SequentialDeclaration.of(declaration)
        while (currentToken?.kind == Token.Kind.SEMICOLON) {
            acceptIt()
            sequentialDeclaration = SequentialDeclaration.of(parseSingleDeclaration(), sequentialDeclaration)
        }
        return sequentialDeclaration
    }

    private fun parseSingleDeclaration(): Declaration {
        return when (currentToken?.kind) {
            Token.Kind.CONST -> {
                acceptIt()
                val identifier = parseIdentifier()
                accept(Token(Token.Kind.IS, Characters.TILDE.toString()))
                val expression = parseExpression()
                ConstDeclaration(identifier, expression)
            }
            Token.Kind.VAR -> {
                acceptIt()
                val identifier = parseIdentifier()
                accept(Token(Token.Kind.COLON, Characters.COLON.toString()))
                val typeDenoter = SimpleTypeDenoter(parseIdentifier())
                ValDeclaration(identifier, typeDenoter)
            }
            else -> {
                throwError()
            }
        }
    }

    private fun parseOperator(): Operator {
        val token = currentToken ?: throwError()
        return if (token.kind == Token.Kind.OPERATOR) {
            currentToken = scanner.scan()
            Operator(token.spelling)
        } else {
            throwError()
        }
    }

    private fun parseIdentifier(): Identifier {
        val token = currentToken ?: throwError()
        return if (token.kind == Token.Kind.IDENTIFIER) {
            currentToken = scanner.scan()
            Identifier(token.spelling)
        } else {
            throwError()
        }
    }

    private fun parseIntegerLiteral(): IntegerLiteral {
        val token = currentToken ?: throwError()
        return if (token.kind == Token.Kind.INT_LITERAL) {
            currentToken = scanner.scan()
            IntegerLiteral(token.spelling)
        } else {
            throwError()
        }
    }
}