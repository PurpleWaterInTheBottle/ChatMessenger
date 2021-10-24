package xyz.gorelov.chatmessenger.remote.account

import xyz.gorelov.chatmessenger.domain.account.AccountEntity
import xyz.gorelov.chatmessenger.remote.core.BaseResponse

class AuthResponse (
    success: Int,
    message: String,
    val user: AccountEntity
) : BaseResponse(success, message)