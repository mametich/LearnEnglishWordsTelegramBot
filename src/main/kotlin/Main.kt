import java.io.File

data class Word(
    var questionWord: String,
    var translate: String,
    var correctAnswersCount: Int = 0,
)

fun Question.asConsoleToString() : String {
    val variants = this.variants
        .mapIndexed { index: Int, word: Word -> "${index + 1} - ${word.translate}"}
        .joinToString(separator = "\n")
    return this.correctAnswer.questionWord + "\n" + variants + "\n0 - выйти в меню"
}

fun main() {

    val trainer = LearnedWordTrainer(3,4)

    while (true) {
        println("Меню: 1 - Учить слова, 2 – Статистика, 0 – Меню")
        when (readln().toIntOrNull()) {
            1 -> {
                while (true) {
                    val question = trainer.getNextQuestion()
                    if (question == null) {
                        println("Все слова выучены")
                        break
                    } else {
                        println(question.asConsoleToString())
                    }

                    val inputNumberFromUser = readln().toIntOrNull() ?: break

                    trainer.checkAnswer(inputNumberFromUser.minus(1))

                    if (trainer.checkAnswer(inputNumberFromUser.minus(1))) {
                        println("Правильно!")
                    } else {
                        println("Не правильно! ${question.correctAnswer.questionWord} - это ${question.correctAnswer.translate}")
                    }
                }
            }

            2 -> {
                val statistics = trainer.getStatistics()
                println(
                    "Выучено: ${statistics.listOfCorrectAnswer} из ${statistics.sizeOfWords} слов | " +
                            "${"%.0f".format(statistics.procentOfCorrectAnswer)}%"
                )
            }

            0 -> println("Меню: 1 - Учить слова, 2 – Статистика, 0 – Меню")
            else -> println("Вы ввели не 1 или 2 или 0")
        }
    }
}