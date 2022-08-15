data class Node(
    val depth: Int,
    val name: String
) {

    init {
        val indent = buildString {
            for (i in 0..depth) {
                append("  ")
            }
        }
        println(indent + name)
    }

    fun child(childNodeName: String): Node = Node(depth + 1, childNodeName)
}