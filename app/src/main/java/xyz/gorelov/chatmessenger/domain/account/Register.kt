package xyz.gorelov.chatmessenger.domain.account

import xyz.gorelov.chatmessenger.domain.interactor.UseCase
import xyz.gorelov.chatmessenger.domain.type.Either
import xyz.gorelov.chatmessenger.domain.type.None
import xyz.gorelov.chatmessenger.domain.type.exception.Failure
import javax.inject.Inject

class Register @Inject constructor(
    private val repository: AccountRepository
) : UseCase<None, Register.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return repository.register(params.email, params.name, params.password)
    }

    data class Params(val email: String, val name: String, val password: String)
}