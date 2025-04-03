package uk.ac.tees.mad.journeysnap.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

object Utils {
    fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
        val file = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpg")
        file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    fun uriToBase64(context: Context, imageUri: Uri, maxWidth: Int = 800, maxHeight: Int = 800, quality: Int = 50): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, maxWidth, maxHeight, true)

            val outputStream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            val byteArray = outputStream.toByteArray()

            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}