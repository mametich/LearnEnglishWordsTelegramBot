import java.io.File

data class Statistics(
    val listOfCorrectAnswer: Int,
    val sizeOfWords: Int,
    var procentOfCorrectAnswer: Double,
)

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word,
)

class LearnedWordTrainer(
    private var countForRemember: Int,
    private var quantityOfWord: Int,
) {
    private var question: Question? = null
    private val dictionary = loadDictionary()


    fun getStatistics(): Statistics {
        val listOfCorrectAnswer = dictionary.filter { it.correctAnswersCount >= countForRemember }.size
        val sizeOfWords = dictionary.size
        val procentOfCorrectAnswer = (listOfCorrectAnswer / sizeOfWords * 100).toDouble()
        return Statistics(listOfCorrectAnswer, sizeOfWords, procentOfCorrectAnswer)
    }

    fun getNextQuestion(): Question? {
        val notLearnedWords = dictionary.filter { it.correctAnswersCount <= countForRemember }.shuffled()
        if (notLearnedWords.isEmpty()) return null

        val listOfChooseWords = notLearnedWords.shuffled().take(quantityOfWord)

        if (listOfChooseWords.size < quantityOfWord) {
            val listOfLearnedWords =
                dictionary.filter { it.correctAnswersCount >= countForRemember }.shuffled()

            val showListOfWord =
                listOfChooseWords + listOfLearnedWords.take(quantityOfWord - listOfChooseWords.size)

            val currentOriginalWord = listOfChooseWords.random()

            question = Question(
                variants = listOfChooseWords,
                correctAnswer = currentOriginalWord
            )
        }
        return question
    }

        fun checkAnswer(userAnswerIndex: Int?): Boolean {
            return question?.let {
                val indexFromWord = it.variants.indexOf(it.correctAnswer)
                if (indexFromWord == userAnswerIndex) {
                    it.correctAnswer.correctAnswersCount++
                    saveDictionary(dictionary)
                    true
                } else {
                    false
                }
            } ?: false
        }


        private fun loadDictionary(): List<Word> {
            val dictionary = mutableListOf<Word>()
            val wordsFile = File("words.txt")

            wordsFile.readLines().forEach {
                val splitLine = it.split("|")
                if (splitLine.size == 3) {
                    dictionary.add(Word(splitLine[0], splitLine[1], splitLine[2].toIntOrNull() ?: 0))
                }
            }
            return dictionary
        }

        private fun saveDictionary(words: List<Word>) {
            val wordsFile = File("words.txt")
            wordsFile.writeText("")
            for (word in words) {
                wordsFile.appendText("${word.questionWord}|${word.translate}|${word.correctAnswersCount}\n")
            }
        }
    }


