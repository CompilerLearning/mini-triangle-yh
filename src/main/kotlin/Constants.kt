object Characters {

    val letters: CharArray = charArrayOf(
        'a', 'b', 'c', 'd', 'e',
        'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y',
        'z',

        'A', 'B', 'C', 'D', 'E',
        'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O',
        'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y',
        'Z'
    )

    val digits: CharArray = charArrayOf(
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    )

    val operator: CharArray = charArrayOf(
        '+', '-', '*', '/', '<', '>', '=', '\\'
    )

    const val SEMICOLON: Char = ';'

    const val COLON: Char = ':'

    const val TILDE: Char = '~'

    const val EQUAL: Char = '='

    const val LEFT_PAREN: Char = '('

    const val RIGHT_PAREN: Char = ')'

    const val EOT: Char = 0x04.toChar()

    const val EXCLAMATION_MARK: Char = '!'

    const val SPACE: Char = ' '

    const val EOL: Char = '\n'

}

object ReservedWords {
    const val BEGIN = "begin"

    const val CONST = "const"

    const val DO = "do"

    const val ELSE = "else"

    const val END = "end"

    const val IF = "if"

    const val IN = "in"

    const val LET = "let"

    const val THEN = "then"

    const val VAR = "var"

    const val WHILE = "while"
}
