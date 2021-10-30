package xyz.gorelov.chatmessenger.ui.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import xyz.gorelov.chatmessenger.databinding.ItemFriendBinding
import xyz.gorelov.chatmessenger.domain.friends.FriendEntity
import xyz.gorelov.chatmessenger.ui.core.BaseAdapter

open class FriendsAdapter : BaseAdapter<FriendsAdapter.FriendViewHolder>() {

    override fun createHolder(parent: ViewGroup): FriendViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendBinding.inflate(layoutInflater, parent, false)
        return FriendViewHolder(binding)
    }

    class FriendViewHolder(val binding: ItemFriendBinding) : BaseViewHolder(binding.root) {
        init {
            binding.btnRemove.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? FriendEntity)?.let {
                binding.friend = it
            }
        }
    }
}