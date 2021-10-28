package xyz.gorelov.chatmessenger.ui.friends

import android.os.Bundle
import android.view.View
import xyz.gorelov.chatmessenger.R
import xyz.gorelov.chatmessenger.domain.friends.FriendEntity
import xyz.gorelov.chatmessenger.domain.type.None
import xyz.gorelov.chatmessenger.presentation.viewmodel.FriendsViewModel
import xyz.gorelov.chatmessenger.ui.App
import xyz.gorelov.chatmessenger.ui.core.BaseListFragment
import xyz.gorelov.chatmessenger.ui.core.ext.onFailure
import xyz.gorelov.chatmessenger.ui.core.ext.onSuccess

class FriendRequestsFragment : BaseListFragment() {
    override val viewAdapter = FriendRequestsAdapter()

    override val layoutId = R.layout.inner_fragment_list

    lateinit var friendsViewModel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        base {
            friendsViewModel = viewModel {
                onSuccess(friendRequestsData, ::handleFriendRequests)
                onSuccess(approveFriendData, ::handleFriendRequestAction)
                onSuccess(cancelFriendData, ::handleFriendRequestAction)
                onFailure(failureData, ::handleFailure)
            }
        }

        setOnItemClickListener { item, v ->
            (item as? FriendEntity)?.let {
                when (v.id) {
                    R.id.btnApprove -> {
                        showProgress()
                        friendsViewModel.approveFriend(it)
                    }
                    R.id.btnCancel -> {
                        showProgress()
                        friendsViewModel.cancelFriend(it)
                    }
                    else -> {
                        activity?.let { act ->
                            navigator.showUser(act, it)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showProgress()
        friendsViewModel.getFriendRequests()
    }

    private fun handleFriendRequests(requests: List<FriendEntity>?) {
        hideProgress()
        if (requests != null) {
            viewAdapter.clear()
            viewAdapter.add(requests)
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun handleFriendRequestAction(none: None?) {
        hideProgress()
        friendsViewModel.getFriendRequests()
    }
}