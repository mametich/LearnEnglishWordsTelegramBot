import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


fun main() {

    val telegramBotService = TelegramBotService()

    var updateId = 0
    var chatId = 0
    var textMessage = ""

    while (true) {
        Thread.sleep(2000)
        val updates: String = telegramBotService.getUpdates(updateId)
        println(updates)
        val sendTextMessage: String = telegramBotService.sendMessage(chatId, textMessage)
        println(sendTextMessage)

        val startUpdateId = updates.lastIndexOf("update_id")
        val endUpdateId = updates.lastIndexOf(",\n\"message\"")
        if (startUpdateId == -1 || endUpdateId == -1) continue
        val updateIdString = updates.substring(startUpdateId + 11, endUpdateId)
        updateId = updateIdString.toInt() + 1

        val messageChatIdRegex: Regex = "\"chat\":\\{\"id\":(.+?),".toRegex()
        val matchResultId: MatchResult? = messageChatIdRegex.find(updates)
        val groupsId = matchResultId?.groups
        val chatIdString = groupsId?.get(1)?.value
        chatId = chatIdString?.toIntOrNull() ?: 0

        val messageTextRegex: Regex = "\"text\":\"(.+?)\"".toRegex()
        val matchResult: MatchResult? = messageTextRegex.find(updates)
        val groups = matchResult?.groups
        val text = groups?.get(1)?.value
        println(text)
        textMessage = text ?: ""
    }
}

