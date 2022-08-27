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

    fun parse(): AST {
        val program = parseProgram()
        if (currentToken?.kind != Token.Kind.EOT) {
            throwError()
        }
        return program
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
        val command = parseSingleCommand(1)
        return Program(0, command)
    }

    private fun parseCommand(depth: Int): Command {
        val command = parseSingleCommand(depth + 1)
        var sequentialCommand = SequentialCommand.of(depth, command)
        while (currentToken?.kind == Token.Kind.SEMICOLON) {
            acceptIt()
            sequentialCommand = SequentialCommand.of(depth, parseSingleCommand(depth + 1), sequentialCommand)
        }
        return sequentialCommand
    }

    private fun parseSingleCommand(depth: Int): Command {
        return when (currentToken?.kind) {
            Token.Kind.IF -> {
                acceptIt()
                val expression = parseExpression(depth + 1)
                accept(Token(Token.Kind.THEN, ReservedWords.THEN))
                val trueCommand = parseSingleCommand(depth + 1)
                accept(Token(Token.Kind.ELSE, ReservedWords.ELSE))
                val falseCommand = parseSingleCommand(depth + 1)
                IfCommand(depth, expression, trueCommand, falseCommand)
            }
            Token.Kind.WHILE -> {
                acceptIt()
                val expression = parseExpression(depth + 1)
                accept(Token(Token.Kind.DO, ReservedWords.DO))
                val command = parseSingleCommand(depth + 1)
                WhileCommand(depth, expression, command)
            }
            Token.Kind.LET -> {
                acceptIt()
                val declaration = parseDeclaration(depth + 1)
                accept(Token(Token.Kind.IN, ReservedWords.IN))
                val command = parseSingleCommand(depth + 1)
                LetCommand(depth, declaration, command)
            }
            Token.Kind.BEGIN -> {
                acceptIt()
                val command = parseCommand(depth)
                accept(Token(Token.Kind.END, ReservedWords.END))
                command
            }
            Token.Kind.IDENTIFIER -> {
                val identifier = parseIdentifier(depth + 1)
                when (currentToken?.kind) {
                    Token.Kind.BECOMES -> {
                        acceptIt()
                        val expression = parseExpression(depth + 1)
                        identifier.apply { this.depth += 1 }
                        AssignCommand(depth, SimpleVName(depth + 1, identifier), expression)
                    }
                    Token.Kind.LPAREN -> {
                        acceptIt()
                        val expression = parseExpression(depth + 1)
                        accept(Token(Token.Kind.RPAREN, Characters.RIGHT_PAREN.toString()))
                        CallCommand(depth, identifier, expression)
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

    private fun parseExpression(depth: Int): Expression {
        var leftExpression = parsePrimaryExpression(depth)
        while (currentToken?.kind == Token.Kind.OPERATOR) {
            val operator = parseOperator(depth + 1)
            val rightExpression = parsePrimaryExpression(depth + 1)
            leftExpression.apply { this.depth += 1 }
            leftExpression = BinaryExpression(depth, operator, leftExpression, rightExpression)
        }
        return leftExpression
    }

    private fun parsePrimaryExpression(depth: Int): Expression {
        return when (currentToken?.kind) {
            Token.Kind.INT_LITERAL -> {
                val literal = parseIntegerLiteral(depth + 1)
                IntegerExpression(depth, literal)
            }
            Token.Kind.IDENTIFIER -> {
                val vName = SimpleVName(depth + 1, parseIdentifier(depth + 2))
                VNameExpression(depth, vName)
            }
            Token.Kind.OPERATOR -> {
                val operator = parseOperator(depth + 1)
                val expression = parsePrimaryExpression(depth + 1)
                UnaryExpression(depth, operator, expression)
            }
            Token.Kind.LPAREN -> {
                acceptIt()
                val expression = parseExpression(depth + 1)
                accept(Token(Token.Kind.RPAREN, Characters.RIGHT_PAREN.toString()))
                expression
            }
            else -> {
                throwError()
            }
        }
    }

    private fun parseDeclaration(depth: Int): Declaration {
        val declaration = parseSingleDeclaration(depth + 1)
        var sequentialDeclaration = SequentialDeclaration.of(depth, declaration)
        while (currentToken?.kind == Token.Kind.SEMICOLON) {
            acceptIt()
            sequentialDeclaration =
                SequentialDeclaration.of(depth, parseSingleDeclaration(depth + 1), sequentialDeclaration)
        }
        return sequentialDeclaration
    }

    private fun parseSingleDeclaration(depth: Int): Declaration {
        return when (currentToken?.kind) {
            Token.Kind.CONST -> {
                acceptIt()
                val identifier = parseIdentifier(depth + 1)
                accept(Token(Token.Kind.IS, Characters.TILDE.toString()))
                val expression = parseExpression(depth + 1)
                ConstDeclaration(depth, identifier, expression)
            }
            Token.Kind.VAR -> {
                acceptIt()
                val identifier = parseIdentifier(depth + 1)
                accept(Token(Token.Kind.COLON, Characters.COLON.toString()))
                val typeDenoter = SimpleTypeDenoter(depth + 1, parseIdentifier(depth + 2))
                ValDeclaration(depth, identifier, typeDenoter)
            }
            else -> {
                throwError()
            }
        }
    }

    private fun parseOperator(depth: Int): Operator {
        val token = currentToken ?: throwError()
        return if (token.kind == Token.Kind.OPERATOR) {
            currentToken = scanner.scan()
            Operator(depth, token.spelling)
        } else {
            throwError()
        }
    }

    private fun parseIdentifier(depth: Int): Identifier {
        val token = currentToken ?: throwError()
        return if (token.kind == Token.Kind.IDENTIFIER) {
            currentToken = scanner.scan()
            Identifier(depth, token.spelling)
        } else {
            throwError()
        }
    }

    private fun parseIntegerLiteral(depth: Int): IntegerLiteral {
        val token = currentToken ?: throwError()
        return if (token.kind == Token.Kind.INT_LITERAL) {
            currentToken = scanner.scan()
            IntegerLiteral(depth, token.spelling)
        } else {
            throwError()
        }
    }
}