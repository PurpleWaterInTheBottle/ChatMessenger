package xyz.gorelov.chatmessenger.ui.friends

import android.view.View
import kotlinx.android.synthetic.main.item_friend_request.view.*
import xyz.gorelov.chatmessenger.R
import xyz.gorelov.chatmessenger.domain.friends.FriendEntity
import xyz.gorelov.chatmessenger.ui.core.BaseAdapter
import xyz.gorelov.chatmessenger.ui.core.GlideHelper

open class FriendRequestsAdapter : BaseAdapter<FriendRequestsAdapter.FriendRequestViewHolder>() {
    override val layoutRes = R.layout.item_friend_request

    override fun createHolder(view: View, viewType: Int): FriendRequestViewHolder {
        return FriendRequestViewHolder(view)
    }

    class FriendRequestViewHolder(view: View) : BaseViewHolder(view) {
        init {
            view.btnApprove.setOnClickListener {
                onClick?.onClick(item, it)
            }
            view.btnCancel.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? FriendEntity)?.let {
                GlideHelper.loadImage(view.context, it.image, view.imgPhoto, R.drawable.ic_account_circle)
                view.tvName.text = it.name
            }
        }
    }
}