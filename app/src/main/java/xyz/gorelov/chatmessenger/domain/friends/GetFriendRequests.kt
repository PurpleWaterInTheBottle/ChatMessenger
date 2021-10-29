package xyz.gorelov.chatmessenger.domain.friends

import xyz.gorelov.chatmessenger.domain.interactor.UseCase
import xyz.gorelov.chatmessenger.domain.type.None
import javax.inject.Inject

class GetFriendRequests @Inject constructor(
    private val friendsRepository: FriendsRepository
) : UseCase<List<FriendEntity>, Boolean>() {

    override suspend fun run(needFetch: Boolean) = friendsRepository.getFriendRequests(needFetch)
}