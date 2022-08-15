package scanner

import ErrorHelper.throwError
import Character
import java.util.LinkedList
import java.util.Queue

class Scanner(inputText: String) {

    private val inputTextStream: Queue<Char> = LinkedList(inputText.toList())

    private var currentChar: Char

    init {
        if (inputTextStream.isEmpty()) {
            throwError()
        }

        currentChar = inputTextStream.poll()
    }

    private fun scanToken() {
        when {
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

    private fun scanSeparator() {
        when (currentChar) {
            Character.exclamationMark -> scanComment()
            Character.space, Character.eol -> scanSpaceOrNewLine()
        }
    }

    private fun scanIdentifier() {

    }

    private fun scanIntLiteral() {

    }

    private fun scanOperator() {

    }

    private fun scanSemicolon() {

    }

    private fun scanBecomeOrColon() {

    }

    private fun scanIs() {

    }

    private fun scanLparen() {

    }

    private fun scanRparen() {

    }

    private fun scanEot() {

    }

    private fun scanComment() {

    }

    private fun scanSpaceOrNewLine() {

    }

    private fun Char.isLetter(): Boolean = Character.letters.contains(this)

    private fun Char.isDigit(): Boolean = Character.digits.contains(this)

    private fun Char.isOperator(): Boolean = Character.operator.contains(this)

}