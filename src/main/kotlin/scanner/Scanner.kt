package scanner

import ErrorHelper.throwError
import Character
import java.util.LinkedList
import java.util.Queue

class Scanner(inputText: String) {

    private val inputTextStream: Queue<Char> = LinkedList(inputText.toList())

    private var currentChar: Char?

    private var currentSpelling: StringBuffer = StringBuffer()

    init {
        if (inputTextStream.isEmpty()) {
            throwError()
        }

        currentChar = inputTextStream.poll()
    }

    fun scan(): Token? {
        scanSeparators()
        return scanToken()
    }

    private fun take(expected: Char) {
        if (currentChar == expected) {
            currentSpelling.append(currentChar)
            currentChar = inputTextStream.poll()
        } else {
            throwError()
        }
    }

    private fun takeIt() {
        currentSpelling.append(currentChar)
        currentChar = inputTextStream.poll()
    }

    private fun scanToken(): Token? {
        if (currentChar == null) {
            return null
        }

        currentSpelling = StringBuffer("")
        return when {
            currentChar.isLetter() -> scanIdentifier()
            currentChar.isDigit() -> scanIntLiteral()
            currentChar.isOperator() -> scanOperator()
            currentChar == Character.semicolon -> scanSemicolon()
            currentChar == Character.colon -> scanBecomeOrColon()
            currentChar == Character.tilde -> scanIs()
            currentChar == Character.leftParen -> scanLparen()
            currentChar == Character.rightParen -> scanRparen()
            currentChar == Character.eot -> scanEot()
            else -> throwError()
        }
    }

    private fun scanSeparators() {
        while (currentChar.isSeparator()) {
            when (currentChar) {
                Character.exclamationMark -> scanComment()
                Character.space, Character.eol -> takeIt()
            }
        }
    }

    private fun scanIdentifier(): Token {
        takeIt()
        while (currentChar.isLetter() || currentChar.isDigit()) {
            takeIt()
        }
        return Token(Token.Kind.IDENTIFIER, currentSpelling.toString())
    }

    private fun scanIntLiteral(): Token {
        takeIt()
        while (currentChar.isDigit()) {
            takeIt()
        }
        return Token(Token.Kind.INT_LITERAL, currentSpelling.toString())
    }

    private fun scanOperator(): Token {
        takeIt()
        return Token(Token.Kind.OPERATOR, currentSpelling.toString())
    }

    private fun scanSemicolon(): Token {
        takeIt()
        return Token(Token.Kind.SEMICOLON, currentSpelling.toString())
    }

    private fun scanBecomeOrColon(): Token {
        takeIt()
        return if (currentChar == Character.equal) {
            takeIt()
            Token(Token.Kind.BECOMES, currentSpelling.toString())
        } else {
            Token(Token.Kind.COLON, currentSpelling.toString())
        }
    }

    private fun scanIs(): Token {
        takeIt()
        return Token(Token.Kind.IS, currentSpelling.toString())
    }

    private fun scanLparen(): Token {
        takeIt()
        return Token(Token.Kind.LPAREN, currentSpelling.toString())
    }

    private fun scanRparen(): Token {
        takeIt()
        return Token(Token.Kind.RPAREN, currentSpelling.toString())
    }

    private fun scanEot(): Token {
        return Token(Token.Kind.EOT, currentSpelling.toString())
    }

    private fun scanComment() {
        takeIt()
        while (currentChar.isGraphic()) {
            takeIt()
            take(Character.eol)
        }
    }

    private fun Char?.isLetter(): Boolean = this != null && Character.letters.contains(this)

    private fun Char?.isDigit(): Boolean = this != null && Character.digits.contains(this)

    private fun Char?.isOperator(): Boolean = this != null && Character.operator.contains(this)

    private fun Char?.isSeparator(): Boolean =
        this == Character.space || this == Character.eol || this == Character.exclamationMark

    private fun Char?.isGraphic(): Boolean = this != null && this.code in 32..126

}