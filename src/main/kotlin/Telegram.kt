fun main(args: Array<String>) {

    val botToken = args[0]

    val telegramBotService = TelegramBotService(botToken)

    var lastUpdateId = 0
    var updateId = 0
    var chatId = 0
    var textMessage = ""
    var dataMessage = ""

    val messageChatIdRegex: Regex = "\"chat\":\\{\"id\":(.+?),".toRegex()
    val messageTextRegex: Regex = "\"text\":\"(.+?)\"".toRegex()
    val dataRegex: Regex = "\"data\":\"(.+?)\"".toRegex()
    val updateIdRegex: Regex = "\"update_id\":(\\d+)".toRegex()

    
    while (true) {
        Thread.sleep(2000)
        val updates: String = telegramBotService.getUpdates(lastUpdateId)
        println(updates)

        val matchUpdateId: MatchResult? = updateIdRegex.find(updates)
        val groupsUpdateId = matchUpdateId?.groups
        val updateIdString = groupsUpdateId?.get(1)?.value
        updateId = updateIdString?.toIntOrNull() ?: continue
        lastUpdateId = updateId + 1

        val matchResultId: MatchResult? = messageChatIdRegex.find(updates)
        val groupsId = matchResultId?.groups
        val chatIdString = groupsId?.get(1)?.value
        chatId = chatIdString?.toIntOrNull() ?: 0

        val matchResult: MatchResult? = messageTextRegex.find(updates)
        val groups = matchResult?.groups
        val text = groups?.get(1)?.value
        textMessage = text ?: ""

        val matchResultData: MatchResult? = dataRegex.find(updates)
        val groupsData = matchResultData?.groups
        val textData = groupsData?.get(1)?.value
        dataMessage = textData ?: ""

        if (textMessage.lowercase() == "hello") {
            telegramBotService.sendMessage(chatId, textMessage)
        }
        if (textMessage.lowercase() == "menu") {
            telegramBotService.sendMenu(chatId)
        }
        if (dataMessage.lowercase() == STATISTIC_CLICKED) {
            telegramBotService.sendMessage(chatId, "Выучено 10 из 10 слов")
        }

        // Внутри условия, получи следующий вопрос из trainer’а.
        // Если вопрос отсутствует, отправь сообщение "Вы выучили все слова в базе",
        // в ином случае осуществи отправку вопроса (sendQuestion())
        // Я понимаю в этом классе нужно создать объект класcа learnedWordTrainer
        if (dataMessage.lowercase() == LEARNS_WORDS_CLICKED) {

        }
    }
    //не могу понять этот метод сюда добавлять? для проверки ответа
    fun checkNextQuestionAndSend(trainer: LearnWordsTrainer, botToken: String, chatId: Int) {

    }

}

