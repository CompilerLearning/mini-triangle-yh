import parser.Parser
import scanner.Scanner

fun main(args: Array<String>) {
    val input = """
       let
            const m ~ 7;
            var n: Integer
       in
            begin
            n := 2 * m * m;
            end
    """

    val scanner = Scanner(input)
    val parser = Parser(scanner)
    parser.parse()
}