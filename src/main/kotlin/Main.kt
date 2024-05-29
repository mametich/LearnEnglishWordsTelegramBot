import java.io.File

private const val COUNT_FOR_REMEMBER = 3
private const val QUANTITY_OF_WORD = 4

fun main() {

    val dictionary = mutableListOf<Word>()

    val wordsFile = File("words.txt")
    wordsFile.createNewFile()

    val listOfWords = wordsFile.readLines()

    for (line in listOfWords) {
        val split = line.split("|")
        val word = Word(
            original = split[0],
            translate = split[1],
            correctAnswersCount = split[2].toIntOrNull() ?: 0
        )
        dictionary.add(word)

    }

    println("Меню: 1 - Учить слова, 2 – Статистика, 0 – Выход")

    while (true) {

        val numberFromUser = readlnOrNull()?.toIntOrNull() ?: println("Введите цифру")
        when (numberFromUser) {
            1 -> {
                val notLearnedWords = dictionary.filter { it.correctAnswersCount < COUNT_FOR_REMEMBER }
                if (notLearnedWords.isEmpty()) {
                    println("Все слова выучены")
                    break
                } else {
                    val currentOriginalWord = notLearnedWords.random()
                    println("Как переводится слово: ${currentOriginalWord.original}")

                    notLearnedWords.take(QUANTITY_OF_WORD).shuffled().forEachIndexed { index, word ->
                        println("${index + 1} - ${word.translate}")
                    }
                }
            }

            2 -> {
                val listOfCorrectAnswer = dictionary.filter { it.correctAnswersCount >= COUNT_FOR_REMEMBER }.size
                val sizeOfWords = dictionary.size
                val procentOfCorrectAnswer = (listOfCorrectAnswer.toDouble() / sizeOfWords) * 100
                println(
                    "Выучено: $listOfCorrectAnswer из $sizeOfWords слов | " +
                            "${"%.0f".format(procentOfCorrectAnswer)}%"
                )
            }

            0 -> break
            else -> println("Вы ввели не 1 или 2 или 0")
        }
    }
}

data class Word(
    var original: String,
    var translate: String,
    var correctAnswersCount: Int = 0,
)