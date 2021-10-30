package xyz.gorelov.chatmessenger.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import xyz.gorelov.chatmessenger.domain.messages.GetChats
import xyz.gorelov.chatmessenger.domain.messages.GetMessagesWithContact
import xyz.gorelov.chatmessenger.domain.messages.MessageEntity
import xyz.gorelov.chatmessenger.domain.messages.SendMessage
import xyz.gorelov.chatmessenger.domain.type.None
import javax.inject.Inject

class MessagesViewModel @Inject constructor(
    val getChatsUseCase: GetChats,
    val getMessagesUseCase: GetMessagesWithContact,
    val sendMessageUseCase: SendMessage
): BaseViewModel() {
    val getChatsData: MutableLiveData<List<MessageEntity>> = MutableLiveData()
    val getMessagesData: MutableLiveData<List<MessageEntity>> = MutableLiveData()
    val sendMessageData: MutableLiveData<None> = MutableLiveData()

    fun getChats(needFetch: Boolean = false) {
        getChatsUseCase(GetChats.Params(needFetch)) { it.either(::handleFailure) { handleGetChats(it, !needFetch)} }
    }

    fun getMessages(contactId: Long, needFetch: Boolean = false) {
        getMessagesUseCase(GetMessagesWithContact.Params(contactId, needFetch)) {
            it.either(::handleFailure) { handleGetMessages(it, contactId, !needFetch) }
        }
    }

    fun sendMessage(toId: Long, message: String, image: String) {
        sendMessageUseCase(SendMessage.Params(toId, message, image)) { it.either(::handleFailure) { handleSendMessage(it, toId)} }
    }

    private fun handleGetChats(messages: List<MessageEntity>, fromCache: Boolean) {
        getChatsData.value = messages
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getChats(true)
        }
    }

    private fun handleGetMessages(messages: List<MessageEntity>, contactId: Long, fromCache: Boolean) {
        getMessagesData.value = messages
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getMessages(contactId, true)
        }
    }

    private fun handleSendMessage(none: None, contactId: Long) {
        sendMessageData.value = none

        getMessages(contactId, true)
    }

    override fun onCleared() {
        super.onCleared()
        getChatsUseCase.unsubscribe()
        getMessagesUseCase.unsubscribe()
        sendMessageUseCase.unsubscribe()
    }
}