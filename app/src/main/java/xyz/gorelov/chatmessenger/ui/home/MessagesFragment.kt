package xyz.gorelov.chatmessenger.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.gorelov.chatmessenger.R
import xyz.gorelov.chatmessenger.cache.ChatDatabase
import xyz.gorelov.chatmessenger.domain.messages.MessageEntity
import xyz.gorelov.chatmessenger.presentation.viewmodel.MediaViewModel
import xyz.gorelov.chatmessenger.presentation.viewmodel.MessagesViewModel
import xyz.gorelov.chatmessenger.remote.service.ApiService
import xyz.gorelov.chatmessenger.ui.App
import xyz.gorelov.chatmessenger.ui.core.BaseListFragment
import xyz.gorelov.chatmessenger.ui.core.ext.onFailure
import xyz.gorelov.chatmessenger.ui.core.ext.onSuccess
import kotlinx.android.synthetic.main.fragment_messages.*

class MessagesFragment : BaseListFragment() {
    override val viewAdapter = MessagesAdapter()

    override val titleToolbar = R.string.chats
    override val layoutId = R.layout.fragment_messages

    lateinit var messagesViewModel: MessagesViewModel
    lateinit var mediaViewModel: MediaViewModel

    private var contactId = 0L
    private var contactName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (lm as? LinearLayoutManager)?.apply {
            stackFromEnd = true
        }

        messagesViewModel = viewModel {
            onSuccess(progressData, ::updateProgress)
            onFailure(failureData, ::handleFailure)
        }

        mediaViewModel = viewModel {
            onSuccess(cameraFileCreatedData, ::onCameraFileCreated)
            onSuccess(pickedImageData, ::onImagePicked)
            onSuccess(progressData, ::updateProgress)
            onFailure(failureData, ::handleFailure)
        }

        base {
            val args = intent.getBundleExtra("args")

            if (args != null) {
                contactId = args.getLong(ApiService.PARAM_CONTACT_ID)
                contactName = args.getString(ApiService.PARAM_NAME, "")
            } else {
                contactId = intent.getLongExtra(ApiService.PARAM_CONTACT_ID, 0L)
                contactName = intent.getStringExtra(ApiService.PARAM_NAME).toString()
            }
        }

        btnSend.setOnClickListener {
            sendMessage()
        }

        btnPhoto.setOnClickListener {
            base {
                navigator.showPickFromDialog(this) { fromCamera ->
                    if (fromCamera) {
                        mediaViewModel.createCameraFile()
                    } else {
                        navigator.showGallery(this)
                    }
                }
            }
        }

        ChatDatabase.getInstance(requireContext()).messagesDao.getLiveMessagesWithContact(contactId)
            .observe(this.viewLifecycleOwner, Observer {
                handleMessages(it)
            })

        viewAdapter.setOnClick(click = { any, view ->
            when (view.id) {
                R.id.imgPhoto -> {
                    (view as? ImageView)?.let {
                        navigator.showImageDialog(requireContext(), it.drawable)
                    }
                }
            }
        }, longClick = { any, view ->
            navigator.showDeleteMessageDialog(requireContext()) {
                (any as? MessageEntity)?.let {
                    messagesViewModel.deleteMessage(contactId, it.id)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        base {
            supportActionBar?.title = contactName
        }

        messagesViewModel.getMessages(contactId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (::mediaViewModel.isInitialized) {
            mediaViewModel.onPickImageResult(requestCode, resultCode, data)
        }
    }

    private fun handleMessages(messages: List<MessageEntity>?) {
        if (messages != null && messages.isNotEmpty()) {
            viewAdapter.submitList(messages)
            Handler().postDelayed({
                recyclerView.smoothScrollToPosition(viewAdapter.itemCount - 1)
            }, 100)
        }
    }

    private fun sendMessage(image: String = "") {
        if (contactId == 0L) return

        val text = etText.text.toString()

        if (text.isBlank() && image.isBlank()) {
            showMessage("Введите текст")
            return
        }

        showProgress()

        messagesViewModel.sendMessage(contactId, text, image)

        etText.text.clear()
    }

    private fun onCameraFileCreated(uri: Uri?) {
        base {
            if (uri != null) {
                navigator.showCamera(this, uri)
            }
        }
    }

    private fun onImagePicked(pickedImage: MediaViewModel.PickedImage?) {
        if (pickedImage != null) {
            sendMessage(pickedImage.string)
        }
    }
}