package xyz.gorelov.chatmessenger.ui.user

import android.os.Bundle
import xyz.gorelov.chatmessenger.ui.App
import xyz.gorelov.chatmessenger.ui.core.BaseActivity
import xyz.gorelov.chatmessenger.ui.core.BaseFragment

class UserActivity : BaseActivity() {
    override var fragment: BaseFragment = UserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}