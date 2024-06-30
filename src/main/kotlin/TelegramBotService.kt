import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private const val API = "6968182819:AAFHSXRmw-zj6mAGKYgU5bT8Bvz24h-tk0U"

class TelegramBotService {

    fun sendMessage(id: Int, message: String) : String {
        val urlGetMe = "https://api.telegram.org/bot$API/sendMessage?chat_id=$id&text=$message"

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
        val urlGetMe = "https://api.telegram.org/bot$API/getUpdates?offset=$updateId"

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