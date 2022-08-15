import scanner.Scanner

fun main(args: Array<String>) {
    val input = "let var y: Integer"
    val scanner = Scanner(input)

    var token = scanner.scan()
    while (token != null) {
        println(token)
        token = scanner.scan()
    }
}