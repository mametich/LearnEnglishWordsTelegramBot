import java.io.File

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
            correctAnswersCount = split[2].toIntOrNull() ?: 0 )
        dictionary.add(word)

    }


    while (true) {
        println("Меню: 1 - Учить слова, 2 – Статистика, 0 – Выход")
        val numberFromUser = readlnOrNull()?.toIntOrNull() ?: println("Введите цифру")
        when(numberFromUser){
            1 -> println("Вы нажали 1")
            2 -> println("Выучено: ${dictionary.filter { it.correctAnswersCount!! >= 3 }.size} из" +
                " ${dictionary.size} слов |" +
                        "${(dictionary.filter { it.correctAnswersCount!! >= 3 }.size.toDouble() / 
                                dictionary.size) * 100}%")
            0 -> break
            else -> println("Вы ввели не 1 или 2 или 0")
        }
    }
}

data class Word(
    var original: String,
    var translate: String,
    var correctAnswersCount: Int? = 0,
)