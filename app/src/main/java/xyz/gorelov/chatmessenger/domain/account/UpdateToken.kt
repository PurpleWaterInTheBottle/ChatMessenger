package xyz.gorelov.chatmessenger.domain.account

import xyz.gorelov.chatmessenger.domain.interactor.UseCase
import xyz.gorelov.chatmessenger.domain.type.None
import javax.inject.Inject

class UpdateToken @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<None, UpdateToken.Params>() {

    override suspend fun run(params: Params) = accountRepository.updateAccountToken(params.token)

    data class Params(val token: String)
}