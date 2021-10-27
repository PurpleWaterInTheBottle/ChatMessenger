package xyz.gorelov.chatmessenger.remote.friends

import xyz.gorelov.chatmessenger.domain.friends.FriendEntity
import xyz.gorelov.chatmessenger.remote.core.BaseResponse

class GetFriendsResponse (
    success: Int,
    message: String,
    val friends: List<FriendEntity>
) : BaseResponse(success, message)