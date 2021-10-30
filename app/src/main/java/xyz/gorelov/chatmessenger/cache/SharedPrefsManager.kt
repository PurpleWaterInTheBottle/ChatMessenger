package xyz.gorelov.chatmessenger.cache

import android.content.SharedPreferences
import xyz.gorelov.chatmessenger.domain.account.AccountEntity
import xyz.gorelov.chatmessenger.domain.type.Either
import xyz.gorelov.chatmessenger.domain.type.None
import xyz.gorelov.chatmessenger.domain.type.Failure
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(private val prefs: SharedPreferences) {
    companion object {
        const val ACCOUNT_TOKEN = "account_token"
        const val ACCOUNT_ID = "account_id"
        const val ACCOUNT_NAME = "account_name"
        const val ACCOUNT_EMAIL = "account_email"
        const val ACCOUNT_STATUS = "account_status"
        const val ACCOUNT_DATE = "account_date"
        const val ACCOUNT_IMAGE = "account_image"
        const val ACCOUNT_PASSWORD = "account_password"
    }

    fun saveToken(token: String): Either<Failure, None> {
        prefs.edit().apply {
            putString(ACCOUNT_TOKEN, token)
        }.apply()

        return Either.Right(None())
    }

    fun getToken(): Either<Failure, String> {
        val str = prefs.getString(ACCOUNT_TOKEN, "")
        return Either.Right(str?:"")
    }

    fun saveAccount(account: AccountEntity): Either<Failure, None> {
        prefs.edit().apply {
            putSafely(ACCOUNT_ID, account.id)
            putSafely(ACCOUNT_NAME, account.name)
            putSafely(ACCOUNT_EMAIL, account.email)
            putSafely(ACCOUNT_TOKEN, account.token)
            putString(ACCOUNT_STATUS, account.status)
            putSafely(ACCOUNT_DATE, account.userDate)
            putSafely(ACCOUNT_IMAGE, account.image)
            putSafely(ACCOUNT_PASSWORD, account.password)
        }.apply()

        return Either.Right(None())
    }

    fun getAccount(): Either<Failure, AccountEntity> {
        val id = prefs.getLong(ACCOUNT_ID, 0)

        if (id == 0L) {
            return Either.Left(Failure.NoSavedAccountsError)
        }

        val account = AccountEntity(
            prefs.getLong(ACCOUNT_ID, 0),
            prefs.getString(ACCOUNT_NAME, "").toString(),
            prefs.getString(ACCOUNT_EMAIL, "").toString(),
            prefs.getString(ACCOUNT_TOKEN, "").toString(),
            prefs.getString(ACCOUNT_STATUS, "").toString(),
            prefs.getLong(ACCOUNT_DATE, 0),
            prefs.getString(ACCOUNT_IMAGE, "").toString(),
            prefs.getString(ACCOUNT_PASSWORD, "").toString()
        )

        return Either.Right(account)
    }

    fun removeAccount(): Either<Failure, None> {
        prefs.edit().apply {
            remove(ACCOUNT_ID)
            remove(ACCOUNT_NAME)
            remove(ACCOUNT_EMAIL)
            remove(ACCOUNT_STATUS)
            remove(ACCOUNT_DATE)
            remove(ACCOUNT_IMAGE)
            remove(ACCOUNT_PASSWORD)
        }.apply()

        return Either.Right(None())
    }

    fun containsAnyAccount(): Either<Failure, Boolean> {
        val id = prefs.getLong(ACCOUNT_ID, 0)
        return Either.Right(id != 0L)
    }
}

fun SharedPreferences.Editor.putSafely(key: String, value: Long?) {
    if (value != null && value != 0L) {
        putLong(key, value)
    }
}

fun SharedPreferences.Editor.putSafely(key: String, value: String?) {
    if (value != null && value.isNotEmpty()) {
        putString(key, value)
    }
}