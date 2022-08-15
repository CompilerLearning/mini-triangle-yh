package parser

import ErrorHelper.throwError
import scanner.Scanner
import scanner.Token

class Parser(
    private val scanner: Scanner
) {

    var currentToken: Token? = scanner.scan()

    fun parse() {

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
}