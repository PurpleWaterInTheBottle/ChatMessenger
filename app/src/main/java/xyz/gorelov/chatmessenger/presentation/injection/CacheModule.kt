package xyz.gorelov.chatmessenger.presentation.injection

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import xyz.gorelov.chatmessenger.cache.AccountCacheImpl
import xyz.gorelov.chatmessenger.cache.SharedPrefsManager
import xyz.gorelov.chatmessenger.data.account.AccountCache
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideAccountCache(prefsManager: SharedPrefsManager): AccountCache = AccountCacheImpl(prefsManager)
}