package xyz.gorelov.chatmessenger.cache

import xyz.gorelov.chatmessenger.data.account.AccountCache
import xyz.gorelov.chatmessenger.domain.type.Either
import xyz.gorelov.chatmessenger.domain.type.None
import xyz.gorelov.chatmessenger.domain.type.exception.Failure
import javax.inject.Inject

class AccountCacheImpl @Inject constructor(private val prefsManager: SharedPrefsManager) :
    AccountCache {

    override fun saveToken(token: String): Either<Failure, None> {
        return prefsManager.saveToken(token)
    }

    override fun getToken(): Either<Failure, String> {
        return prefsManager.getToken()
    }
}