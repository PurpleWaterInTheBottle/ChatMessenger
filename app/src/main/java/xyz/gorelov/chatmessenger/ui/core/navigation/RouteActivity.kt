package xyz.gorelov.chatmessenger.ui.core.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.gorelov.chatmessenger.ui.App
import javax.inject.Inject

class RouteActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        navigator.showMain(this)
    }
}