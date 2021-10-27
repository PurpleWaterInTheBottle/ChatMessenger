package xyz.gorelov.chatmessenger.data.friends

import xyz.gorelov.chatmessenger.domain.friends.FriendEntity
import xyz.gorelov.chatmessenger.domain.type.Either
import xyz.gorelov.chatmessenger.domain.type.Failure
import xyz.gorelov.chatmessenger.domain.type.None

interface FriendsRemote {
    fun getFriends(userId: Long, token: String): Either<Failure, List<FriendEntity>>
    fun getFriendRequests(userId: Long, token: String): Either<Failure, List<FriendEntity>>

    fun approveFriendRequest(userId: Long, requestUserId: Long, friendsId: Long, token: String): Either<Failure, None>
    fun cancelFriendRequest(userId: Long, requestUserId: Long, friendsId: Long, token: String): Either<Failure, None>

    fun addFriend(email: String, userId: Long, token: String): Either<Failure, None>
    fun deleteFriend(userId: Long, requestUserId: Long, friendsId: Long, token: String): Either<Failure, None>
}