package xyz.gorelov.chatmessenger.presentation.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import xyz.gorelov.chatmessenger.data.account.AccountCache
import xyz.gorelov.chatmessenger.data.account.AccountRemote
import xyz.gorelov.chatmessenger.data.account.AccountRepositoryImpl
import xyz.gorelov.chatmessenger.data.friends.FriendsRemote
import xyz.gorelov.chatmessenger.data.friends.FriendsRepositoryImpl
import xyz.gorelov.chatmessenger.data.media.MediaRepositoryImpl
import xyz.gorelov.chatmessenger.domain.account.AccountRepository
import xyz.gorelov.chatmessenger.domain.friends.FriendsRepository
import xyz.gorelov.chatmessenger.domain.media.MediaRepository
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

    @Provides
    @Singleton
    fun provideFriendsRepository(remote: FriendsRemote, accountCache: AccountCache): FriendsRepository {
        return FriendsRepositoryImpl(accountCache, remote)
    }

    @Provides
    @Singleton
    fun provideMediaRepository(context: Context): MediaRepository {
        return MediaRepositoryImpl(context)
    }
}