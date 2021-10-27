package xyz.gorelov.chatmessenger.presentation.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import xyz.gorelov.chatmessenger.presentation.viewmodel.AccountViewModel
import xyz.gorelov.chatmessenger.presentation.viewmodel.FriendsViewModel
import xyz.gorelov.chatmessenger.presentation.viewmodel.ViewModelFactory

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FriendsViewModel::class)
    abstract fun bindFriendsViewModel(friendsViewModel: FriendsViewModel): ViewModel
}