package parser

import Characters
import ErrorHelper.throwError
import Node
import ReservedWords
import scanner.Scanner
import scanner.Token

class Parser(
    private val scanner: Scanner
) {

    private var currentToken: Token? = scanner.scan()

    fun parse() {
        parseProgram(Node(0, "Program"))
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

    private fun parseProgram(node: Node) {
        parseSingleCommand(node.child("single-Command"))
    }

    private fun parseCommand(node: Node) {
        parseSingleCommand(node.child("single-Command"))
        while (currentToken?.kind == Token.Kind.SEMICOLON) {
            acceptIt()
            parseSingleCommand(node.child("single-Command"))
        }
    }

    private fun parseSingleCommand(node: Node) {
        when (currentToken?.kind) {
            Token.Kind.IF -> {
                acceptIt()
                parseExpression(node.child("Expression"))
                accept(Token(Token.Kind.THEN, ReservedWords.THEN))
                parseSingleCommand(node.child("single-Command"))
                accept(Token(Token.Kind.ELSE, ReservedWords.ELSE))
                parseSingleCommand(node.child("single-Command"))
            }
            Token.Kind.WHILE -> {
                acceptIt()
                parseExpression(node.child("Expression"))
                accept(Token(Token.Kind.DO, ReservedWords.DO))
                parseSingleCommand(node.child("single-Command"))
            }
            Token.Kind.LET -> {
                acceptIt()
                parseDeclaration(node.child("Declaration"))
                accept(Token(Token.Kind.IN, ReservedWords.IN))
                parseSingleCommand(node.child("single-Command"))
            }
            Token.Kind.BEGIN -> {
                acceptIt()
                parseCommand(node.child("Command"))
                accept(Token(Token.Kind.END, ReservedWords.END))
            }
            Token.Kind.IDENTIFIER -> {
                parseIdentifier(node.child("Identifier"))
                when (currentToken?.kind) {
                    Token.Kind.BECOMES -> {
                        acceptIt()
                        parseExpression(node.child("Expression"))
                    }
                    Token.Kind.LPAREN -> {
                        acceptIt()
                        parseExpression(node.child("Expression"))
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

    private fun parseExpression(node: Node) {
        parsePrimaryExpression(node.child("primary-Expression"))
        while (currentToken?.kind == Token.Kind.OPERATOR) {
            parseOperator(node.child("Operator"))
            parsePrimaryExpression(node.child("primary-Expression"))
        }
    }

    private fun parsePrimaryExpression(node: Node) {
        when (currentToken?.kind) {
            Token.Kind.INT_LITERAL -> {
                parseIntegerLiteral(node.child("Int-Literal"))
            }
            Token.Kind.IDENTIFIER -> {
                parseIdentifier(node.child("Identifier"))
            }
            Token.Kind.OPERATOR -> {
                parseOperator(node.child("Operator"))
            }
            Token.Kind.LPAREN -> {
                acceptIt()
                parseExpression(node.child("Expression"))
                accept(Token(Token.Kind.RPAREN, Characters.RIGHT_PAREN.toString()))
            }
            else -> {
                throwError()
            }
        }
    }

    private fun parseDeclaration(node: Node) {
        parseSingleDeclaration(node.child("single-Declaration"))
        while (currentToken?.kind == Token.Kind.SEMICOLON) {
            acceptIt()
            parseSingleDeclaration(node.child("single-Declaration"))
        }
    }

    private fun parseSingleDeclaration(node: Node) {
        when (currentToken?.kind) {
            Token.Kind.CONST -> {
                acceptIt()
                parseIdentifier(node.child("Identifier"))
                accept(Token(Token.Kind.IS, Characters.TILDE.toString()))
                parseExpression(node.child("Expression"))
            }
            Token.Kind.VAR -> {
                acceptIt()
                parseIdentifier(node.child("Identifier"))
                accept(Token(Token.Kind.COLON, Characters.COLON.toString()))
                parseIdentifier(node.child("Identifier"))
            }
            else -> {
                throwError()
            }
        }
    }

    private fun parseOperator(node: Node) {
        if (currentToken?.kind == Token.Kind.OPERATOR) {
            currentToken = scanner.scan()
        } else {
            throwError()
        }
    }

    private fun parseIdentifier(node: Node) {
        if (currentToken?.kind == Token.Kind.IDENTIFIER) {
            currentToken = scanner.scan()
        } else {
            throwError()
        }
    }

    private fun parseIntegerLiteral(node: Node) {
        if (currentToken?.kind == Token.Kind.INT_LITERAL) {
            currentToken = scanner.scan()
        } else {
            throwError()
        }
    }
}