package xyz.gorelov.chatmessenger.ui.home

import android.os.Bundle
import xyz.gorelov.chatmessenger.ui.App
import xyz.gorelov.chatmessenger.ui.core.BaseActivity
import xyz.gorelov.chatmessenger.ui.core.BaseFragment

class MessagesActivity : BaseActivity() {
    override var fragment: BaseFragment = MessagesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}