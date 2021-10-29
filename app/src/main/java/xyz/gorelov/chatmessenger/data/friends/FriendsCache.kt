package xyz.gorelov.chatmessenger.data.friends

import xyz.gorelov.chatmessenger.domain.friends.FriendEntity

interface FriendsCache {
    fun saveFriend(entity: FriendEntity)

    fun getFriend(key: Long): FriendEntity?

    fun getFriends(): List<FriendEntity>

    fun getFriendRequests(): List<FriendEntity>

    fun removeFriendEntity(key: Long)
}