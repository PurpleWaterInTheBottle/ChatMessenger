package xyz.gorelov.chatmessenger.remote.messages

import xyz.gorelov.chatmessenger.domain.messages.MessageEntity
import xyz.gorelov.chatmessenger.remote.core.BaseResponse

class GetMessagesResponse (
    success: Int,
    message: String,
    val messages: List<MessageEntity>
) : BaseResponse(success, message)