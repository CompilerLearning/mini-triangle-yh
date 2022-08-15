object Character {

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

    const val semicolon: Char = ';'

    const val colon: Char = ':'

    const val tilde: Char = '~'

    const val equal: Char = '='

    const val leftParen: Char = '('

    const val rightParen: Char = ')'

    const val eot: Char = 0x04.toChar()

    const val exclamationMark: Char = '!'

    const val space: Char = ' '

    const val eol: Char = '\n'

}

