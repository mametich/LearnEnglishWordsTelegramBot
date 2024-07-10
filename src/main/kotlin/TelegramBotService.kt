import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

private const val API_REQUEST = "https://api.telegram.org/bot"
private const val LEARNS_WORDS_CLICKED = "learns_words_clicked"
private const val STATISTIC_CLICKED = "statistic_clicked"

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
}