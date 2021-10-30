package xyz.gorelov.chatmessenger.data.messages

import xyz.gorelov.chatmessenger.domain.messages.MessageEntity

interface MessagesCache {
    fun saveMessage(entity: MessageEntity)

    fun getChats(): List<MessageEntity>

    fun getMessagesWithContact(contactId: Long): List<MessageEntity>
}