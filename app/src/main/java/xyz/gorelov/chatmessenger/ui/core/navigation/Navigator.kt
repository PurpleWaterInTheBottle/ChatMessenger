package xyz.gorelov.chatmessenger.ui.core.navigation

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.dialog_image.view.*
import xyz.gorelov.chatmessenger.R
import xyz.gorelov.chatmessenger.domain.friends.FriendEntity
import xyz.gorelov.chatmessenger.presentation.Authenticator
import xyz.gorelov.chatmessenger.presentation.viewmodel.MediaViewModel
import xyz.gorelov.chatmessenger.remote.service.ApiService
import xyz.gorelov.chatmessenger.ui.account.AccountActivity
import xyz.gorelov.chatmessenger.ui.core.PermissionManager
import xyz.gorelov.chatmessenger.ui.home.HomeActivity
import xyz.gorelov.chatmessenger.ui.home.MessagesActivity
import xyz.gorelov.chatmessenger.ui.login.ForgetPasswordActivity
import xyz.gorelov.chatmessenger.ui.login.LoginActivity
import xyz.gorelov.chatmessenger.ui.register.RegisterActivity
import xyz.gorelov.chatmessenger.ui.user.UserActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor(
    private val authenticator: Authenticator,
    private val permissionManager: PermissionManager
) {

    fun showMain(context: Context) {
        authenticator.userLoggedIn {
            if (it) showHome(context, false) else showLogin(context, false)
        }
    }

    fun showLogin(context: Context, newTask: Boolean = true) = context.startActivity<LoginActivity>(newTask = newTask)

    fun showForgedPassword(context: Context) = context.startActivity<ForgetPasswordActivity>()

    fun showHome(context: Context, newTask: Boolean = true) = context.startActivity<HomeActivity>(newTask = newTask)

    fun showSignUp(context: Context) = context.startActivity<RegisterActivity>()

    fun showEmailNotFoundDialog(context: Context, email: String) {
        AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.message_promt_app))

            .setPositiveButton(android.R.string.yes) { dialog, which ->
                showEmailInvite(context, email)
            }

            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun showEmailInvite(context: Context, email: String) {
        val appPackageName = context.packageName
        val emailIntent = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null
            )
        )
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.resources.getString(R.string.message_subject_promt_app))
        emailIntent.putExtra(
            Intent.EXTRA_TEXT, context.resources.getString(R.string.message_text_promt_app) + " "
                    + context.resources.getString(R.string.url_google_play) + appPackageName
        )
        context.startActivity(Intent.createChooser(emailIntent, "??????????????????"))
    }

    fun showAccount(context: Context) {
        context.startActivity<AccountActivity>()
    }

    fun showUser(context: Context, friendEntity: FriendEntity) {
        val bundle = Bundle()
        bundle.putString(ApiService.PARAM_IMAGE, friendEntity.image)
        bundle.putString(ApiService.PARAM_NAME, friendEntity.name)
        bundle.putString(ApiService.PARAM_EMAIL, friendEntity.email)
        bundle.putString(ApiService.PARAM_STATUS, friendEntity.status)
        bundle.putLong(ApiService.PARAM_CONTACT_ID, friendEntity.id)
        context.startActivity<UserActivity>(args = bundle)
    }

    fun showChatWithContact(contactId: Long, contactName: String, context: Context) {
        val bundle = Bundle()
        bundle.putLong(ApiService.PARAM_CONTACT_ID, contactId)
        bundle.putString(ApiService.PARAM_NAME, contactName)
        context.startActivity<MessagesActivity>(args = bundle)
    }

    fun showPickFromDialog(activity: AppCompatActivity, onPick: (fromCamera: Boolean) -> Unit) {
        val options = arrayOf<CharSequence>(
            activity.getString(R.string.camera),
            activity.getString(R.string.gallery)
        )

        val builder = AlertDialog.Builder(activity)

        builder.setTitle(activity.getString(R.string.pick))
        builder.setItems(options) { _, item ->
            when (options[item]) {
                activity.getString(R.string.camera) -> {
                    permissionManager.checkCameraPermission(activity) {
                        onPick(true)
                    }
                }
                activity.getString(R.string.gallery) -> {
                    permissionManager.checkWritePermission(activity) {
                        onPick(false)
                    }
                }
            }
        }
        builder.show()
    }

    fun showCamera(activity: AppCompatActivity, uri: Uri) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        activity.startActivityForResult(intent, MediaViewModel.CAPTURE_IMAGE_REQUEST_CODE)
    }

    fun showGallery(activity: AppCompatActivity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"

        activity.startActivityForResult(intent, MediaViewModel.PICK_IMAGE_REQUEST_CODE)
    }

    fun showDeleteMessageDialog(context: Context, onPositive: () -> Unit) {
        AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.remove_message))

            .setPositiveButton(android.R.string.yes) { dialog, which ->
                onPositive()
                dialog.dismiss()
            }

            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    fun showImageDialog(context: Context, image: Drawable) {
        val view = LayoutInflater.from(context).inflate(
            R.layout.dialog_image,
            null
        )

        val dialog = Dialog(context, R.style.DialogFullscreen)

        view.imageView.setImageDrawable(image)
        dialog.setContentView(view)

        view.imageView.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
}

private inline fun <reified T> Context.startActivity(newTask: Boolean = false, args: Bundle? = null) {
    this.startActivity(Intent(this, T::class.java).apply {
        if (newTask) {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        putExtra("args", args)
    })
}