import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

const val API_REQUEST = "https://api.telegram.org/bot"
const val LEARNS_WORDS_CLICKED = "learns_words_clicked"
const val STATISTIC_CLICKED = "statistic_clicked"
const val CALLBACK_DATA_ANSWER_PREFIX = "answer_"

class TelegramBotService(private val token: String) {

    fun sendMessage(id: Int, message: String): String {
        val encoded = URLEncoder.encode(
            message,
            StandardCharsets.UTF_8
        )
        println(encoded)

        val urlSendMessage = "$API_REQUEST${token}/sendMessage?chat_id=$id&text=$encoded"

        val client: HttpClient = HttpClient
            .newBuilder()
            .build()

        val request = HttpRequest
            .newBuilder()
            .uri(URI.create(urlSendMessage))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }

    fun getUpdates(updateId: Int): String {
        val urlGetMe = "$API_REQUEST${token}/getUpdates?offset=$updateId"

        val client: HttpClient = HttpClient
            .newBuilder()
            .build()

        val request = HttpRequest
            .newBuilder()
            .uri(URI.create(urlGetMe))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }

    fun sendMenu(id: Int): String {
        val urlSendMenu = "$API_REQUEST${token}/sendMessage"

        val sendMenuBody = """
            {
                "chat_id":$id,
                    "text": "Основное меню",
                    "reply_markup": {
                    "inline_keyboard": [
                    [
                        {
                            "text":"Изучить слова",
                            "callback_data":"$LEARNS_WORDS_CLICKED"
                        },
                    {
                            "text":"Статистика",
                            "callback_data":"$STATISTIC_CLICKED"
                        }
                    ]
                    ]
                }   
            }
        """.trimIndent()

        val client: HttpClient = HttpClient
            .newBuilder()
            .build()

        val request = HttpRequest
            .newBuilder()
            .uri(URI.create(urlSendMenu))
            .header("Content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(sendMenuBody))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }

    fun sendQuestion(botToken: String, chatId: Int, question: Question): String {
        val urlSendQuestion = "$API_REQUEST${token}/sendMessage"

        val sendQuestion = """
            {
                "chat_id":$chatId,
                    "text": "${question.correctAnswer.questionWord}",
                    "reply_markup": {
                    "inline_keyboard": [
                    [
                        {
                            "text":"${question.correctAnswer.translate}",
                            "callback_data":"$CALLBACK_DATA_ANSWER_PREFIX" + "${List(question.variants.size) { index ->
                                "$index. " }
        }"
                        },
                        {
                            "text":"${question.correctAnswer.translate}",
                            "callback_data":"$CALLBACK_DATA_ANSWER_PREFIX" + "${question.variants.mapIndexed { index, _ ->
                                "$index. " }}"
                        },
                         {
                            "text":"${question.variants.shuffled().take(1)}",
                            "callback_data":"$CALLBACK_DATA_ANSWER_PREFIX" + "${question.variants.mapIndexed { index, _ ->
                                "$index. " }}"
                        },
                         {
                            "text":"${question.variants.shuffled().take(1)}",
                            "callback_data":"$CALLBACK_DATA_ANSWER_PREFIX" + "${question.variants.mapIndexed { index, _ ->
                                "$index. " }}"
                        }
                    ]
                    ]
                }   
            }
        """.trimIndent()

        val client: HttpClient = HttpClient
            .newBuilder()
            .build()

        val request = HttpRequest
            .newBuilder()
            .uri(URI.create(urlSendQuestion))
            .header("Content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(sendQuestion))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }
}

