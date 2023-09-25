package com.example.imagefilter.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File

class SavedImageRepositoryImpl(private val context: Context): SaveImageRepository {
    override suspend fun loadSavedImages(): ArrayList<Pair<File, Bitmap>>? {
        val savedImages = ArrayList<Pair<File, Bitmap>>()
        var dir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Saved Images")
        dir.listFiles()?.let {data ->
            data.forEach {
                savedImages.add(Pair(it, getPreviewBitmap(it)))
            }
            return savedImages

        }?: return null

    }

    private fun getPreviewBitmap(file: File): Bitmap {
        val originalBitmap = BitmapFactory.decodeFile(file.absolutePath)
        val width = 150
        val height = ((originalBitmap.height * width) / originalBitmap.width)
        return Bitmap.createScaledBitmap(originalBitmap, width, height, false)
    }
}