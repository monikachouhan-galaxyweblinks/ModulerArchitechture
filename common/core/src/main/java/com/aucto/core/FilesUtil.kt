package com.aucto.core;

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.*

// import java.io.*

/**
 * @Created on 23/8/19.
 * @author GWL
 */
object FilesUtil {

    // region - Constant properties
    private const val VIDEO_DIRECTORY_NAME = "Videos"
    private const val GOSPEL_DIRECTORY_NAME = "Gospel_Videos"
    private const val THUMBNAIL_DIRECTORY_NAME = "Thumbnails"
    private const val GOSPEL_THUMBNAIL_DIRECTORY_NAME = "Gospel_Thumbnails"
    private const val GOSPEL_PDF_DIRECTORY_NAME = "gospel_pdf"
    private const val PDF_DIRECTORY_NAME = "Pdf"
    private const val FAVORITE_DIRECTORY_NAME = "favorite"
    // endregion

    // region - Public function


    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, null, null)
        return Uri.parse(path)
    }

    fun Activity.getRealPathFromURI(uri: Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        val path = cursor.getString(idx)
        cursor.close()
        return path
    }

    fun Activity.getRealPathFromGalleryUri(contentUri: Uri): String? {
        val out: OutputStream
        val file = File(getFilename(this))
        try {
            if (file.createNewFile()) {
                val iStream = contentResolver.openInputStream(contentUri)
                val inputData = FileUtil.getBytes(iStream)
                out = FileOutputStream(file)
                out.write(inputData)
                out.close()
                return file.absolutePath
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun getFilename(context: Context): String? {
        val mediaStorageDir = File(context.filesDir.absolutePath)
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs()
        }
        val mImageName = "IMG_" + System.currentTimeMillis() + ".png"
        return mediaStorageDir.absolutePath + "/" + mImageName
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }
    // endregion
}
