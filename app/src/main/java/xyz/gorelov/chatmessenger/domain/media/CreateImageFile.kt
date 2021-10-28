package xyz.gorelov.chatmessenger.domain.media

import android.net.Uri
import xyz.gorelov.chatmessenger.domain.interactor.UseCase
import xyz.gorelov.chatmessenger.domain.type.None
import javax.inject.Inject

class CreateImageFile @Inject constructor(
    private val mediaRepository: MediaRepository
) : UseCase<Uri, None>() {

    override suspend fun run(params: None) = mediaRepository.createImageFile()
}