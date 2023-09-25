package com.example.imagefilter.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagefilter.repositories.SaveImageRepository
import com.example.imagefilter.utilities.Coroutines
import java.io.File

class SavedImagesViewModel(private val savedImageRepository: SaveImageRepository): ViewModel() {
    private val savedImageDataState = MutableLiveData<SaveImageDataState>()
    val savedImageUiState: LiveData<SaveImageDataState> get() = savedImageDataState

    fun loadImages() {
        Coroutines.io {
            runCatching {
                emitSavedImagesUiState(isLoading = true)
                savedImageRepository.loadSavedImages()
            }.onSuccess { savedImages ->
                if (savedImages.isNullOrEmpty()) {
                    emitSavedImagesUiState(error = "No image found")
                }else{
                    emitSavedImagesUiState(savedImage = savedImages)
                }
            }.onFailure {
                emitSavedImagesUiState(error = it.message.toString())
            }
        }
    }

    private fun emitSavedImagesUiState(
        isLoading: Boolean = false,
        savedImage: List<Pair<File, Bitmap>>? = null,
        error: String? = null
    ) {
        val dataState = SaveImageDataState(isLoading, savedImage, error)
        savedImageDataState.postValue(dataState)
    }


    data class SaveImageDataState(
        val isLoading: Boolean,
        val savedImage: List<Pair<File, Bitmap>>?,
        val error: String?
    )
}