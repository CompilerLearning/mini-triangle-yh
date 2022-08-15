package scanner

import ErrorHelper.throwError
import java.util.LinkedList
import java.util.Queue

class Scanner(inputText: String) {

    private val inputTextStream : Queue<Char> = LinkedList(inputText.toList())

    private var currentChar: Char

    init {
        if (inputTextStream.isEmpty()) {
            throwError()
        }

        currentChar = inputTextStream.poll()
    }


}