package xyz.gorelov.chatmessenger.domain.media

import android.graphics.Bitmap
import android.net.Uri
import xyz.gorelov.chatmessenger.domain.type.Either
import xyz.gorelov.chatmessenger.domain.type.Failure

interface MediaRepository {
    fun createImageFile(): Either<Failure, Uri>
    fun encodeImageBitmap(bitmap: Bitmap?): Either<Failure, String>
    fun getPickedImage(uri: Uri?): Either<Failure, Bitmap>
}