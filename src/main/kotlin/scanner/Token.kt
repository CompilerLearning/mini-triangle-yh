package scanner

data class Token(
    val kind: Kind,
    val spelling: String
) {

    enum class Kind {
        IDENTIFIER,
        INT_LITERAL,
        OPERATOR,
        BEGIN,
        CONST,
        DO,
        ELSE,
        END,
        IF,
        IN,
        LET,
        THEN,
        VAR,
        WHILE,
        SEMICOLON,
        COLON,
        BECOMES,
        IS,
        LPAREN,
        RPAREN,
        EOT
    }
}
