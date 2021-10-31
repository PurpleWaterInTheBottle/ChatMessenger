package xyz.gorelov.chatmessenger.domain.messages

import xyz.gorelov.chatmessenger.domain.type.Either
import xyz.gorelov.chatmessenger.domain.type.Failure
import xyz.gorelov.chatmessenger.domain.type.None

interface MessagesRepository {
    fun sendMessage(
        toId: Long,
        message: String,
        image: String
    ): Either<Failure, None>

    fun getChats(needFetch: Boolean): Either<Failure, List<MessageEntity>>

    fun getMessagesWithContact(contactId: Long, needFetch: Boolean): Either<Failure, List<MessageEntity>>

    fun deleteMessagesByUser(messageId: Long): Either<Failure, None>
}