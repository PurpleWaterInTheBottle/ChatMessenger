package xyz.gorelov.chatmessenger.remote.friends

import com.google.gson.annotations.SerializedName
import xyz.gorelov.chatmessenger.domain.friends.FriendEntity
import xyz.gorelov.chatmessenger.remote.core.BaseResponse

class GetFriendRequestsResponse (
    success: Int,
    message: String,
    @SerializedName("friend_requests")
    val friendsRequests: List<FriendEntity>
) : BaseResponse(success, message)