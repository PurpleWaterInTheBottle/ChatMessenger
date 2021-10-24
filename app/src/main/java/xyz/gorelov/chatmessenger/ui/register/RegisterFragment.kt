package xyz.gorelov.chatmessenger.ui.register

import android.os.Bundle
import android.view.View
import xyz.gorelov.chatmessenger.R
import xyz.gorelov.chatmessenger.domain.type.None
import xyz.gorelov.chatmessenger.presentation.viewmodel.AccountViewModel
import xyz.gorelov.chatmessenger.ui.App
import xyz.gorelov.chatmessenger.ui.core.ext.onFailure
import xyz.gorelov.chatmessenger.ui.core.ext.onSuccess
import kotlinx.android.synthetic.main.fragment_register.*
import xyz.gorelov.chatmessenger.domain.account.AccountEntity
import xyz.gorelov.chatmessenger.ui.core.BaseFragment

class RegisterFragment : BaseFragment() {
    override val layoutId = R.layout.fragment_register
    override val titleToolbar = R.string.register

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        accountViewModel = viewModel {
            onSuccess(registerData, ::handleRegister)
            onSuccess(accountData, ::handleLogin)
            onFailure(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNewMembership.setOnClickListener {
            register()
        }

        btnAlreadyHaveAccount.setOnClickListener {
            activity?.finish()
        }
    }

    private fun validateFields(): Boolean {
        val allFields = arrayOf(etEmail, etPassword, etConfirmPassword, etusername)
        var allValid = true
        for (field in allFields) {
            allValid = field.testValidity() && allValid
        }
        return allValid && validatePasswords()
    }

    private fun validatePasswords(): Boolean {
        val valid = etPassword.text.toString() == etConfirmPassword.text.toString()
        if (!valid) {
            showMessage(getString(R.string.error_password_mismatch))
        }
        return valid
    }

    private fun register() {
        hideSoftKeyboard()

        val allValid = validateFields()

        if (allValid) {
            showProgress()

            accountViewModel.register(
                etEmail.text.toString(),
                etusername.text.toString(),
                etPassword.text.toString()
            )
        }
    }

    private fun handleLogin(accountEntity: AccountEntity?) {
        hideProgress()
        activity?.let {
            navigator.showHome(it)
            it.finish()
        }
    }

    private fun handleRegister(none: None? = None()) {
        hideProgress()
        showMessage("Аккаунт создан")
    }
}