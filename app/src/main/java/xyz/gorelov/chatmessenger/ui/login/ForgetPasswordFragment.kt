package xyz.gorelov.chatmessenger.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_forget_password.*
import xyz.gorelov.chatmessenger.R
import xyz.gorelov.chatmessenger.domain.type.None
import xyz.gorelov.chatmessenger.presentation.viewmodel.AccountViewModel
import xyz.gorelov.chatmessenger.ui.App
import xyz.gorelov.chatmessenger.ui.core.BaseFragment
import xyz.gorelov.chatmessenger.ui.core.ext.onFailure
import xyz.gorelov.chatmessenger.ui.core.ext.onSuccess

class ForgetPasswordFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_forget_password
    override val titleToolbar: Int = R.string.screen_forget_password

    lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountViewModel = viewModel {
            onSuccess(forgetPasswordData, ::onPasswordSent)
            onFailure(failureData, ::handleFailure)
        }

        btnSendPassword.setOnClickListener {
            sendPassword()
        }
    }

    private fun sendPassword() {
        if (etEmail.testValidity()) {
            val email = etEmail.text.toString()
            accountViewModel.forgetPassword(email)
        }
    }

    private fun onPasswordSent(none: None?) {
        Toast.makeText(requireContext(), getString(R.string.email_sent), Toast.LENGTH_SHORT).show()
        activity?.finish()
    }
}