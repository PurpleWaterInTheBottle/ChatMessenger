package xyz.gorelov.chatmessenger.domain.account

import xyz.gorelov.chatmessenger.domain.interactor.UseCase
import xyz.gorelov.chatmessenger.domain.type.Either
import xyz.gorelov.chatmessenger.domain.type.Failure
import xyz.gorelov.chatmessenger.domain.type.None
import javax.inject.Inject

class CheckAuth @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<Boolean, None>() {

    override suspend fun run(params: None): Either<Failure, Boolean> = accountRepository.checkAuth()
}