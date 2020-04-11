package com.myjob.jobclub.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.widget.ImageView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Throws(IOException::class)
internal fun createImageFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    storageDir?.let {
        if (!it.exists()) {
            storageDir.mkdirs()
        }
    }
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

internal fun setScaledBitmap(imageFilePath: String, photoImageView: ImageView): Bitmap {
    val imageViewWidth = photoImageView.width
    val imageViewHeight = photoImageView.height

    val bmOptions = BitmapFactory.Options()
    bmOptions.inJustDecodeBounds = true
    BitmapFactory.decodeFile(imageFilePath, bmOptions)
    val bitmapWidth = bmOptions.outWidth
    val bitmapHeight = bmOptions.outHeight

    val scaleFactor = Math.min(bitmapWidth / imageViewWidth, bitmapHeight / imageViewHeight)

    bmOptions.inJustDecodeBounds = false
    bmOptions.inSampleSize = scaleFactor

    return BitmapFactory.decodeFile(imageFilePath, bmOptions)
}