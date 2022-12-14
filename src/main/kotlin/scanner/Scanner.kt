package scanner

import ErrorHelper.throwError
import Characters
import ReservedWords
import java.util.LinkedList
import java.util.Queue

class Scanner(inputText: String) {

    private val inputTextStream: Queue<Char> = LinkedList(inputText.toList())

    private var currentChar: Char? = inputTextStream.poll()

    private var currentSpelling: StringBuffer = StringBuffer()

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
            currentChar == Characters.SEMICOLON -> scanSemicolon()
            currentChar == Characters.COLON -> scanBecomeOrColon()
            currentChar == Characters.TILDE -> scanIs()
            currentChar == Characters.LEFT_PAREN -> scanLparen()
            currentChar == Characters.RIGHT_PAREN -> scanRparen()
            currentChar == Characters.EOT -> scanEot()
            else -> throwError()
        }
    }

    private fun scanSeparators() {
        while (currentChar.isSeparator()) {
            when (currentChar) {
                Characters.EXCLAMATION_MARK -> scanComment()
                Characters.SPACE, Characters.EOL -> takeIt()
            }
        }
    }

    private fun scanIdentifier(): Token {
        takeIt()
        while (currentChar.isLetter() || currentChar.isDigit()) {
            takeIt()
        }
        return when (currentSpelling.toString()) {
            ReservedWords.BEGIN -> Token(Token.Kind.BEGIN, currentSpelling.toString())
            ReservedWords.CONST -> Token(Token.Kind.CONST, currentSpelling.toString())
            ReservedWords.DO -> Token(Token.Kind.DO, currentSpelling.toString())
            ReservedWords.ELSE -> Token(Token.Kind.ELSE, currentSpelling.toString())
            ReservedWords.END -> Token(Token.Kind.END, currentSpelling.toString())
            ReservedWords.IF -> Token(Token.Kind.IF, currentSpelling.toString())
            ReservedWords.IN -> Token(Token.Kind.IN, currentSpelling.toString())
            ReservedWords.LET -> Token(Token.Kind.LET, currentSpelling.toString())
            ReservedWords.THEN -> Token(Token.Kind.THEN, currentSpelling.toString())
            ReservedWords.VAR -> Token(Token.Kind.VAR, currentSpelling.toString())
            ReservedWords.WHILE -> Token(Token.Kind.WHILE, currentSpelling.toString())
            else -> Token(Token.Kind.IDENTIFIER, currentSpelling.toString())
        }
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
        return if (currentChar == Characters.EQUAL) {
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
            take(Characters.EOL)
        }
    }

    private fun Char?.isLetter(): Boolean = this != null && Characters.letters.contains(this)

    private fun Char?.isDigit(): Boolean = this != null && Characters.digits.contains(this)

    private fun Char?.isOperator(): Boolean = this != null && Characters.operator.contains(this)

    private fun Char?.isSeparator(): Boolean =
        this == Characters.SPACE || this == Characters.EOL || this == Characters.EXCLAMATION_MARK

    private fun Char?.isGraphic(): Boolean = this != null && this.code in 32..126

}