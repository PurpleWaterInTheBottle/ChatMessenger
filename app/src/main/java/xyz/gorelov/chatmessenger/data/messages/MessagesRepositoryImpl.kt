package xyz.gorelov.chatmessenger.data.messages

import xyz.gorelov.chatmessenger.data.account.AccountCache
import xyz.gorelov.chatmessenger.domain.messages.MessageEntity
import xyz.gorelov.chatmessenger.domain.messages.MessagesRepository
import xyz.gorelov.chatmessenger.domain.type.*

class MessagesRepositoryImpl (
    private val messagesRemote: MessagesRemote,
    private val messagesCache: MessagesCache,
    private val accountCache: AccountCache
) : MessagesRepository {

    override fun getChats(needFetch: Boolean): Either<Failure, List<MessageEntity>> {
        return accountCache.getCurrentAccount().flatMap { account ->
            return@flatMap if (needFetch) {
                messagesRemote.getChats(account.id, account.token).onNext {
                    it.map { message ->
                        if (message.senderId == account.id) {
                            message.fromMe = true
                        }
                        messagesCache.saveMessage(message)
                    }
                }
            } else {
                Either.Right(messagesCache.getChats())
            }
        }
            .map { it.distinctBy {
                it.contact?.id }
            }
    }

    override fun getMessagesWithContact(contactId: Long, needFetch: Boolean): Either<Failure, List<MessageEntity>> {
        return accountCache.getCurrentAccount().flatMap { account ->
            return@flatMap if (needFetch) {
                messagesRemote.getMessagesWithContact(contactId, account.id, account.token).onNext {
                    it.map { message ->

                        if (message.senderId == account.id) {
                            message.fromMe = true
                        }

                        val contact = messagesCache.getChats().first { it.contact?.id == contactId }.contact
                        message.contact = contact

                        messagesCache.saveMessage(message)
                    }
                }
            } else {
                Either.Right(messagesCache.getMessagesWithContact(contactId))
            }
        }
    }

    override fun sendMessage(
        toId: Long,
        message: String,
        image: String
    ): Either<Failure, None> {
        return accountCache.getCurrentAccount().flatMap {
            messagesRemote.sendMessage(it.id, toId, it.token, message, image)
        }
    }
}