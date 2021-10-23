package xyz.gorelov.chatmessenger.presentation.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import xyz.gorelov.chatmessenger.data.account.AccountCache
import xyz.gorelov.chatmessenger.data.account.AccountRemote
import xyz.gorelov.chatmessenger.data.account.AccountRepositoryImpl
import xyz.gorelov.chatmessenger.domain.account.AccountRepository
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = context

    @Provides
    @Singleton
    fun provideAccountRepository(remote: AccountRemote, cache: AccountCache): AccountRepository {
        return AccountRepositoryImpl(remote, cache)
    }
}