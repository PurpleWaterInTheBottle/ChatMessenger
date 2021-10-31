package xyz.gorelov.chatmessenger.domain.messages

import xyz.gorelov.chatmessenger.domain.interactor.UseCase
import xyz.gorelov.chatmessenger.domain.type.None
import javax.inject.Inject

class DeleteMessage @Inject constructor(
    private val messagesRepository: MessagesRepository
) : UseCase<None, DeleteMessage.Params>() {

    override suspend fun run(params: Params) = messagesRepository.deleteMessagesByUser(params.messageId)

    data class Params(val messageId: Long)
}