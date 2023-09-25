package com.example.imagefilter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import com.example.imagefilter.adapters.SavedImagesAdapter
import com.example.imagefilter.databinding.ActivitySavedImagesBinding
import com.example.imagefilter.listener.SavedImagesListener
import com.example.imagefilter.utilities.displayToast
import com.example.imagefilter.viewmodels.SavedImagesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class SavedImagesActivity : AppCompatActivity(), SavedImagesListener {
    private lateinit var binding: ActivitySavedImagesBinding
    private val viewModel: SavedImagesViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObserver()
        setListeners()
        viewModel.loadImages()
    }

    private fun setObserver() {
        viewModel.savedImageUiState.observe(this, {
          val dataState = it?:return@observe
            binding.savingProgressBar.visibility =
                if (dataState.isLoading) View.INVISIBLE else View.GONE
            dataState.savedImage?.let {savedImages ->
                SavedImagesAdapter(savedImages, this).also { adapter ->
                    with(binding.recyclerSaved) {
                        this.adapter = adapter
                        visibility = View.VISIBLE
                    }
                }
            }?: run {
                dataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
    }

    private fun setListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onImageClicked(file: File) {
        val fileUri = FileProvider.getUriForFile(
            applicationContext,
            "${packageName}.provider",
            file
        )
        Intent(applicationContext, FilteredImageActivity::class.java).also {
            it.putExtra(EditImageActivity.FILTERED_IMAGE_URI, fileUri)
            startActivity(it)
        }
    }
}