package com.example.imagefilter.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.imagefilter.adapters.ImageFiltersAdapter
import com.example.imagefilter.databinding.ActivityEditImageBinding
import com.example.imagefilter.listener.ImageFilterListener
import com.example.imagefilter.model.ImageFilter
import com.example.imagefilter.utilities.displayToast
import com.example.imagefilter.utilities.show
import com.example.imagefilter.viewmodels.EditImageViewModel
import jp.co.cyberagent.android.gpuimage.GPUImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageActivity : AppCompatActivity(), ImageFilterListener {

    companion object{
        const val FILTERED_IMAGE_URI = "filteredImageUri"
    }


    private lateinit var binding: ActivityEditImageBinding
    private val viewModel: EditImageViewModel by viewModel()
    private lateinit var gpuImage: GPUImage

    //Image Bitmap
    private lateinit var originalBitmap: Bitmap
    private val filteredBitmap = MutableLiveData<Bitmap>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpListeners()
        setObserver()
        displayImagePre()
        prepareImage()
    }



    private fun setObserver() {
        viewModel.imagePreviewUiState.observe(this, {
            val dataState = it ?: return@observe
            binding.progressBar.visibility =
                if (dataState.isLoading) View.VISIBLE else View.GONE

            dataState.bitmap?.let { bitmap ->
                // For the first time 'filtered image = original image'
                originalBitmap = bitmap
                filteredBitmap.value = bitmap
                with(originalBitmap) {
                    gpuImage.setImage(Bitmap.createScaledBitmap(this, bitmap.getWidth(), bitmap.getHeight(), true))
                    binding.imagePreview.show()
                    viewModel.loadImageFilters(this)

                }

            } ?: kotlin.run {
                dataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })

        viewModel.imageFiltersUiState.observe(this, {
            val imageFiltersDataState = it?: return@observe
            binding.filtersProgressBar.visibility =
                if (imageFiltersDataState.isLoading) View.VISIBLE else View.GONE
            imageFiltersDataState.imageFilters?.let { imageFilters ->
                ImageFiltersAdapter(imageFilters, this).also {adapter ->
                    binding.filtersRecyclerView.adapter = adapter
                }

            } ?: kotlin.run {
                imageFiltersDataState.error?.let {error ->
                    displayToast(error)
                }
            }
        })
        filteredBitmap.observe(this, {bitmap ->

            binding.imagePreview.setImageBitmap( Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true))
        })
        filteredBitmap.observe(this, {bitmap ->
            binding.imagePreview.setImageBitmap(bitmap)
        })
        viewModel.saveFilteredImageUiState.observe(this, {
            val saveFilteredImageDataState = it ?: return@observe
            if (saveFilteredImageDataState.isLoading) {
                binding.ivDone.visibility = View.GONE
                binding.savingProgressBar.visibility = View.VISIBLE
            }else {
                binding.ivDone.visibility = View.VISIBLE
                binding.savingProgressBar.visibility = View.GONE
            }
            saveFilteredImageDataState.uri?.let {savedImageUri ->
                Intent(
                    applicationContext,
                    FilteredImageActivity::class.java
                ).also {filteredImageIntent ->
                    filteredImageIntent.putExtra(FILTERED_IMAGE_URI, savedImageUri)
                    startActivity(filteredImageIntent)

                }
            }?: kotlin.run {
                saveFilteredImageDataState.error?.let {error ->
                    displayToast(error)
                }
            }
        })


    }

    private fun prepareImage() {
        gpuImage = GPUImage(applicationContext)
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let {imageUri ->
            viewModel.prepareImagePreview(imageUri)
        }
    }

    private fun decodeString(value : String):Bitmap{
        val bytes = Base64.decode(value, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes,0,bytes.size)
    }

    private fun displayImagePre() {
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
            val inputStream = contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.imagePreview.setImageBitmap(bitmap)
            binding.imagePreview.visibility = View.VISIBLE

        }

    }

    private fun setUpListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.ivDone.setOnClickListener {
            filteredBitmap.value?.let { bitmap ->
                viewModel.saveFilteredImage(bitmap)
            }
        }


        binding.imagePreview.setOnLongClickListener {
            binding.imagePreview.setImageBitmap(originalBitmap)
            return@setOnLongClickListener false
        }
        binding.imagePreview.setOnClickListener {
            binding.imagePreview.setImageBitmap(filteredBitmap.value)
        }
    }


    override fun onFileSelected(imageFilter: ImageFilter) {
        with(imageFilter) {
            with(gpuImage) {
                setFilter(filter)
                filteredBitmap.value= bitmapWithFilterApplied
            }
        }
    }
}