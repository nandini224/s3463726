package uk.ac.tees.mad.journeysnap.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {


    fun bitmapToBase64(bitmap: Bitmap, maxWidth: Int = 800, maxHeight: Int = 800, quality: Int = 50): String? {
        return try {

            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, maxWidth, maxHeight, true)

            val outputStream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            val byteArray = outputStream.toByteArray()

            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            Log.e("BitmapToBase64", "Error: ${e.message}")
            null
        }
    }

    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            Log.e("base64ToBitmap", e.message.toString())
            null
        }
    }

    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            bitmap
        } catch (e: Exception) {
            Log.e("uriToBitmap", e.message.toString())
            null
        }
    }

    fun getDate(timeMillis:Long):String{
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(timeMillis))
    }

}