package com.devstudio.signaturepad.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


object Utils {

    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        // Compress the bitmap to PNG format (you can change it to JPEG if needed)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        // Convert byte array to Base64 string
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }


    fun base64ToBitmap(base64String: String): Bitmap? {
        try {
            // Decode Base64 string to byte array
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            // Convert byte array to Bitmap
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            return null
        }
    }


}