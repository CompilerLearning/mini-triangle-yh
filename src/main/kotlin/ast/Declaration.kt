package ast

sealed class Declaration : AST

class ConstDeclaration(
    val identifier: Identifier,
    val expression: Expression
) : Declaration()

class ValDeclaration(
    val identifier: Identifier,
    val typeDenoter: TypeDenoter
) : Declaration()

class SequentialDeclaration(
    val declarations: List<Declaration>
) : Declaration() {

    companion object {
        fun of(c: Declaration, o: SequentialDeclaration? = null): SequentialDeclaration {
            val declarations = buildList {
                o?.declarations?.let {
                    addAll(it)
                }
                add(c)
            }
            return SequentialDeclaration(declarations)
        }
    }
}