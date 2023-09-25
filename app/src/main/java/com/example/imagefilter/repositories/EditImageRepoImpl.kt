package com.example.imagefilter.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.imagefilter.model.ImageFilter
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorMatrixFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageRGBFilter
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception

class EditImageRepoImpl(private val context: Context) : EditImageRepository {

    override suspend fun getImageFilters(image: Bitmap): List<ImageFilter> {
        val gpuImage = GPUImage(context).apply {
            setImage(image)
        }
        val imageFilters: ArrayList<ImageFilter> = ArrayList()

        //region:: Image Filters

        // Normal
        GPUImageFilter().also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Normal",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        // Yeli
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                1.0f, -0.3831f, 0.3883f, 0.0f,
                0.0f, 1.0f, 0.2f, 0f,
                -0.1961f, 0.0f, 1.0f, 0f,
                0f, 0f, 0f, 1f
            )
        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Yeli",
                    filter = it,

                    filterPreview = gpuImage.bitmapWithFilterApplied
                )

            )
        }
        // Retro
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.2f, 0.0f,
                0.1f, 0.1f, 1.0f, 0.0f,
                1.0f, 0.0f, 0.0f, 1.0f
            )
        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Retro",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        // Just
        GPUImageColorMatrixFilter(
            0.9f,
            floatArrayOf(
                0.4f, 0.6f, 0.5f, 0.0f,
                0.0f, 0.4f, 1.0f, 0.0f,
                0.05f, 0.1f, 0.4f, 0.4f,
                1.0f, 1.0f, 1.0f, 1.0f
            )
        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Just",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        // Hume
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                1.25f, 0.0f, 0.2f, 0.0f,
                0.0f, 1.0f, 0.2f, 0.0f,
                0.0f, 0.3f, 1.0f, 0.3f,
                0.0f, 0.0f, 0.0f, 1.0f
            )
        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Hume",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        // Desert
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(1.2f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.1f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.9f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
            )
        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Desert",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        // Old Times
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                1.0f, 0.5f, 0.0f, 0.0f,
                -0.2f, 1.1f, -0.2f, 0.11f,
                0.2f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
            )
        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Old Times",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }
        // Limo
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                1.0f, 0.0f, 0.8f, 0.0f,
                0.4f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.1f,
                0.0f, 0.0f, 0.0f, 1.0f
            )
        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Limo",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        // Sepia
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                1.0f, 0.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.2f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f

            )
        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Sepia",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }
        //Solar
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                1.5f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Solar",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }
        // Neutron
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                0f, 1f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0.6f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Neutron",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }



        // Bright
        GPUImageRGBFilter(1.1f, 1.3f, 1.6f).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Bright",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }
        // Milk
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.64f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
            )
        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Grey",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }
        // Muli
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                1.2f, 0.0f, 0.0f, 0.0f,
                0.1f, 1.1f, 0.1f, 0.0f,
                0.0f, 0.1f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
            )

        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Muli",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        // Sepia
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                0.393f, 0.769f, 0.189f, 0.0f,
                0.349f, 0.686f, 0.168f, 0.0f,
                0.272f, 0.534f, 0.131f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
            )

        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Vintage",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        // Grayscale
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                0.33f, 0.33f, 0.33f, 0.0f,
                0.33f, 0.33f, 0.33f, 0.0f,
                0.33f, 0.33f, 0.33f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
            )

        ).also {
            gpuImage.setFilter(it)
            imageFilters.add(
                ImageFilter(
                    name = "Grayscale",
                    filter = it,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        //endregion

        return imageFilters


    }

    override suspend fun prepareImagePreview(imageUri: Uri): Bitmap? {
        getInputStreamFromUri(imageUri)?.let { inputStream ->
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            val width = context.resources.displayMetrics.widthPixels
            val height = ((originalBitmap.height * width) / originalBitmap.width)
            return Bitmap.createScaledBitmap(originalBitmap, height, width, false)

        } ?: return null
    }


    private fun getInputStreamFromUri(uri: Uri): InputStream {
        return context.contentResolver.openInputStream(uri)!!
    }

    override suspend fun saveFilteredImage(filteredBitmap: Bitmap): Uri? {
        return try {
            val mediaStorageDirectory = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "Saved Images"
            )
            if (!mediaStorageDirectory.exists()) {
                mediaStorageDirectory.mkdirs()
            }
            val fileName = "IMG_${System.currentTimeMillis()}.jpg"
            val file = File(mediaStorageDirectory, fileName)
            saveFile(file, filteredBitmap)
            FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        } catch (exception: Exception) {
            null
        }
    }

    private fun saveFile(file: File, bitmap: Bitmap) {
        with(FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
            flush()
            close()
        }
    }


}