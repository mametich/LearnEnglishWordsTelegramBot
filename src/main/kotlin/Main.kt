import java.io.File

fun main() {

    val dictionary = mutableListOf<Word>()

    val wordsFile = File("words.txt")
    wordsFile.createNewFile()

    val listOfWords = wordsFile.readLines()

    for (line in listOfWords) {
        val split = line.split("|")
        val word = Word(original = split[0], translate = split[1])
        dictionary.add(word)

    }

    dictionary.forEach {
        println(it)
    }
}

data class Word(
    var original: String,
    var translate: String,
    var correctAnswersCount: Int = 0,
)