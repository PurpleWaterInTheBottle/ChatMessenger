package xyz.gorelov.chatmessenger.presentation

import xyz.gorelov.chatmessenger.domain.account.CheckAuth
import xyz.gorelov.chatmessenger.domain.type.None
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator
@Inject constructor(
    val checkAuth: CheckAuth
){
    fun userLoggedIn(body: (Boolean) -> Unit) {
        checkAuth(None()) {
            it.either({ body(false) }, { body(it) })
        }
    }
}