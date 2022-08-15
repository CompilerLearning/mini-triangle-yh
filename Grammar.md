# Grammar

## Terminal Symbols
| | | | | | |
|--|--|--|--|--|--|
|`begin`|`const`|`else`|`end`|`if`||
|`in`|`let`|`then`|`var`|`while`||
|`;`|`:`|`:=`|`~`|`(`|`)`|
|`+`|`-`|`*`|`/`|`<`|`>`|
|`=`|`/`| | | | |

## Nonterminal Symbols
`Program(start symbol)`

`Command`   
`single-Command`

`Expression`    
`primary-Expression`

`V-name`

`Declaration`   
`single-Declaration`

`Type-denoter`

`Operator`

`Identifier`

`Integer-Literal`

## Production Rules
```
Program             ::= single-Command

Command             ::= single-Command
                    |   Command;   single-Command

single-Command      ::= V-name := Expression
                    |   Identifier
                    |   if Expression then single-Command
                            else single-Command
                    |   while Expression do single-Command
                    |   let Declaration in single-Command
                    |   begin Command end

Expression          ::= primary-Expression
                    |   Expression Operator primary-Expression

primary-Expression  ::= Integer-Literal
                    |   V-name
                    |   Operator primary-Expression
                    |   ( Expression )

V-name              ::= Identifier

Declaration         ::= single-Declaration
                    |   Declaration ; single-Declaration

single-Declaration  ::= const Identifier ~ Expression
                    |   var Identifier : Type-denoter

Type-denoter        ::= Identifier

Operator            ::= + | - | * | / | < | > | = | \

Identifier          ::= Letter | Identifier Letter | Identifier Digit

Integer-Literal     ::= Digit | Integer-Literal Digit

Comment             ::= ! Graphic eol
```


## Production Rules(applied with EBNF)
```
Program             ::= single-Command

Command             ::= single-Command( ; single-Command)*

single-Command      ::= Identifier (:= Expression | (Expression))
                    |   if Expression then single-Command
                            else single-Command
                    |   while Expression do single-Command
                    |   let Declaration in single-Command
                    |   begin Command end

Expression          ::= primary-Expression (Operator primary-Expression)*

primary-Expression  ::= Integer-Literal
                    |   Identifier
                    |   Operator primary-Expression
                    |   ( Expression )

Declaration         ::= single-Declaration (; single-Declaration)*

single-Declaration  ::= const Identifier ~ Expression
                    |   var Identifier : Type-denoter

Type-denoter        ::= Identifier

Operator            ::= + | - | * | / | < | > | = | \

Identifier          ::= Letter (Letter | Digit)*

Integer-Literal     ::= Digit | Integer-Literal Digit

Comment             ::= ! Graphic eol
```

### Lexical grammar
```
Token               ::= Letter(Letter | Digit)* | Digit Digit* |
                        '+' | '-' | '*' | '/' | '<' | '>' | '=' | '\' |
                        ';' | ':' ( '=' | ε ) | '~' | '(' | ')' | eot

Separator           ::= ! Graphic* eol | space | eol
```