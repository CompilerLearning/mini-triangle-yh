package parser

import Characters
import ErrorHelper.throwError
import ReservedWords
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

    private fun parseProgram() {
        parseSingleCommand()
    }

    private fun parseCommand() {
        parseSingleCommand()
        while (currentToken?.kind == Token.Kind.SEMICOLON) {
            acceptIt()
            parseSingleCommand()
        }
    }

    private fun parseSingleCommand() {
        when (currentToken?.kind) {
            Token.Kind.IF -> {
                acceptIt()
                parseExpression()
                accept(Token(Token.Kind.THEN, ReservedWords.THEN))
                parseSingleCommand()
                accept(Token(Token.Kind.ELSE, ReservedWords.ELSE))
                parseSingleCommand()
            }
            Token.Kind.WHILE -> {
                acceptIt()
                parseExpression()
                accept(Token(Token.Kind.DO, ReservedWords.DO))
                parseSingleCommand()
            }
            Token.Kind.LET -> {
                acceptIt()
                parseDeclaration()
                accept(Token(Token.Kind.IN, ReservedWords.IN))
                parseSingleCommand()
            }
            Token.Kind.BEGIN -> {
                acceptIt()
                parseCommand()
                accept(Token(Token.Kind.END, ReservedWords.END))
            }
            Token.Kind.IDENTIFIER -> {
                parseIdentifier()
                when (currentToken?.kind) {
                    Token.Kind.BECOMES -> {
                        acceptIt()
                        parseExpression()
                    }
                    Token.Kind.LPAREN -> {
                        acceptIt()
                        parseExpression()
                        accept(Token(Token.Kind.RPAREN, Characters.RIGHT_PAREN.toString()))
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

    private fun parseExpression() {
        parsePrimaryExpression()
        while (currentToken?.kind == Token.Kind.OPERATOR) {
            parseOperator()
            parsePrimaryExpression()
        }
    }

    private fun parsePrimaryExpression() {
        when (currentToken?.kind) {
            Token.Kind.INT_LITERAL -> {
                parseIntegerLiteral()
            }
            Token.Kind.IDENTIFIER -> {
                parseIdentifier()
            }
            Token.Kind.OPERATOR -> {
                parseOperator()
            }
            Token.Kind.LPAREN -> {
                acceptIt()
                parseExpression()
                accept(Token(Token.Kind.RPAREN, Characters.RIGHT_PAREN.toString()))
            }
            else -> {
                throwError()
            }
        }
    }

    private fun parseDeclaration() {
        parseSingleDeclaration()
        while (currentToken?.kind == Token.Kind.SEMICOLON) {
            acceptIt()
            parseSingleDeclaration()
        }
    }

    private fun parseSingleDeclaration() {
        when (currentToken?.kind) {
            Token.Kind.CONST -> {
                acceptIt()
                parseIdentifier()
                accept(Token(Token.Kind.IS, Characters.TILDE.toString()))
                parseExpression()
            }
            Token.Kind.VAR -> {
                acceptIt()
                parseIdentifier()
                accept(Token(Token.Kind.COLON, Characters.COLON.toString()))
                parseIdentifier()
            }
            else -> {
                throwError()
            }
        }
    }

    private fun parseOperator() {
        if (currentToken?.kind == Token.Kind.OPERATOR) {
            currentToken = scanner.scan()
        } else {
            throwError()
        }
    }

    private fun parseIdentifier() {
        if (currentToken?.kind == Token.Kind.IDENTIFIER) {
            currentToken = scanner.scan()
        } else {
            throwError()
        }
    }

    private fun parseIntegerLiteral() {
        if (currentToken?.kind == Token.Kind.INT_LITERAL) {
            currentToken = scanner.scan()
        } else {
            throwError()
        }
    }
}