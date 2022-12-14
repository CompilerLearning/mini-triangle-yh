import parser.Parser
import scanner.Scanner
import visitor.PrintingVisitor

fun main(args: Array<String>) {
    val input = """
       let
            const m ~ 7;
            var n: Integer
       in
            begin
            n := 2 * m * m
            end
    """

    val scanner = Scanner(input + Characters.EOT)
    val parser = Parser(scanner)
    val ast = parser.parse()

    PrintingVisitor().accept(ast)
}