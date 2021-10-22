package xyz.gorelov.chatmessenger.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import xyz.gorelov.chatmessenger.R
import xyz.gorelov.chatmessenger.cache.AccountCacheImpl
import xyz.gorelov.chatmessenger.cache.SharedPrefsManager
import xyz.gorelov.chatmessenger.data.account.AccountRepositoryImpl
import xyz.gorelov.chatmessenger.domain.account.AccountRepository
import xyz.gorelov.chatmessenger.domain.account.Register
import xyz.gorelov.chatmessenger.remote.account.AccountRemoteImpl
import xyz.gorelov.chatmessenger.remote.core.NetworkHandler
import xyz.gorelov.chatmessenger.remote.core.Request
import xyz.gorelov.chatmessenger.remote.service.ServiceFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        val accountCache = AccountCacheImpl(SharedPrefsManager(sharedPrefs))
        val accountRemote = AccountRemoteImpl(Request(NetworkHandler(this)), ServiceFactory.makeService(true))

        val accountRepository: AccountRepository = AccountRepositoryImpl(accountRemote, accountCache)

        accountCache.saveToken("12345")

        val register = Register(accountRepository)
        register(Register.Params("abcqd@m.com", "abcd", "123")) {
            it.either({
                Toast.makeText(this, it.javaClass.simpleName, Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(this, "Аккаунт создан", Toast.LENGTH_SHORT).show()
            })
        }
    }
}