package com.example.imagefilter.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.example.imagefilter.model.ImageFilter

interface EditImageRepository {
    suspend fun prepareImagePreview(imageUri: Uri): Bitmap?
    suspend fun getImageFilters(image: Bitmap): List<ImageFilter>
    suspend fun saveFilteredImage(filteredBitmap: Bitmap): Uri?

}