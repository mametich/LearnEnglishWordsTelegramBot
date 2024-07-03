import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private const val API_REQUEST = "https://api.telegram.org/bot"

class TelegramBotService(private val token: String) {

    fun sendMessage(id: Int, message: String) : String {
        val urlGetMe = "$API_REQUEST${token}/sendMessage?chat_id=$id&text=$message"

        val client: HttpClient = HttpClient
            .newBuilder()
            .build()

        val request = HttpRequest
            .newBuilder()
            .uri(URI(urlGetMe))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }

    fun getUpdates(updateId: Int): String {
        val urlGetMe = "$API_REQUEST$token/getUpdates?offset=$updateId"

        val client: HttpClient = HttpClient
            .newBuilder()
            .build()

        val request = HttpRequest
            .newBuilder()
            .uri(URI(urlGetMe))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }
}