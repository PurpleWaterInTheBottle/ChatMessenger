package xyz.gorelov.chatmessenger.presentation.injection

import dagger.Module
import dagger.Provides
import xyz.gorelov.chatmessenger.BuildConfig
import xyz.gorelov.chatmessenger.data.account.AccountRemote
import xyz.gorelov.chatmessenger.data.friends.FriendsRemote
import xyz.gorelov.chatmessenger.data.messages.MessagesRemote
import xyz.gorelov.chatmessenger.remote.account.AccountRemoteImpl
import xyz.gorelov.chatmessenger.remote.core.Request
import xyz.gorelov.chatmessenger.remote.friends.FriendsRemoteImpl
import xyz.gorelov.chatmessenger.remote.messages.MessagesRemoteImpl
import xyz.gorelov.chatmessenger.remote.service.ApiService
import xyz.gorelov.chatmessenger.remote.service.ServiceFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService = ServiceFactory.makeService(BuildConfig.DEBUG)

    @Singleton
    @Provides
    fun provideAccountRemote(request: Request, apiService: ApiService): AccountRemote {
        return AccountRemoteImpl(request, apiService)
    }

    @Singleton
    @Provides
    fun provideFriendsRemote(request: Request, apiService: ApiService): FriendsRemote {
        return FriendsRemoteImpl(request, apiService)
    }

    @Singleton
    @Provides
    fun provideMessagesRemote(request: Request, apiService: ApiService): MessagesRemote {
        return MessagesRemoteImpl(request, apiService)
    }
}