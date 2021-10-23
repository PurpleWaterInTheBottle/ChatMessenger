package xyz.gorelov.chatmessenger.ui

import android.app.Application
import dagger.Component
import xyz.gorelov.chatmessenger.presentation.injection.AppModule
import xyz.gorelov.chatmessenger.presentation.injection.CacheModule
import xyz.gorelov.chatmessenger.presentation.injection.RemoteModule
import xyz.gorelov.chatmessenger.presentation.injection.ViewModelModule
import xyz.gorelov.chatmessenger.ui.activity.RegisterActivity
import xyz.gorelov.chatmessenger.ui.fragment.RegisterFragment
import xyz.gorelov.chatmessenger.ui.service.FirebaseService
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

    //fragments
    fun inject(fragment: RegisterFragment)

    //services
    fun inject(service: FirebaseService)
}