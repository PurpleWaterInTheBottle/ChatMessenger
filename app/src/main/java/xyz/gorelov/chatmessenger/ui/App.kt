package xyz.gorelov.chatmessenger.ui

import android.app.Application
import dagger.Component
import xyz.gorelov.chatmessenger.presentation.injection.AppModule
import xyz.gorelov.chatmessenger.presentation.injection.CacheModule
import xyz.gorelov.chatmessenger.presentation.injection.RemoteModule
import xyz.gorelov.chatmessenger.presentation.injection.ViewModelModule
import xyz.gorelov.chatmessenger.ui.core.navigation.RouteActivity
import xyz.gorelov.chatmessenger.ui.register.RegisterActivity
import xyz.gorelov.chatmessenger.ui.register.RegisterFragment
import xyz.gorelov.chatmessenger.ui.firebase.FirebaseService
import xyz.gorelov.chatmessenger.ui.friends.FriendRequestsFragment
import xyz.gorelov.chatmessenger.ui.friends.FriendsFragment
import xyz.gorelov.chatmessenger.ui.home.ChatsFragment
import xyz.gorelov.chatmessenger.ui.home.HomeActivity
import xyz.gorelov.chatmessenger.ui.login.LoginFragment
import javax.inject.Singleton

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }
}

@Singleton
@Component(modules = [AppModule::class, CacheModule::class, RemoteModule::class, ViewModelModule::class])
interface AppComponent {

    //activities
    fun inject(activity: RegisterActivity)
    fun inject(activity: RouteActivity)
    fun inject(activity: HomeActivity)

    //fragments
    fun inject(fragment: RegisterFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: ChatsFragment)
    fun inject(fragment: FriendsFragment)
    fun inject(fragment: FriendRequestsFragment)

    //services
    fun inject(service: FirebaseService)
}