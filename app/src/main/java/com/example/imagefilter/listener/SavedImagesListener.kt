package com.example.imagefilter.listener

import java.io.File

interface SavedImagesListener {
    fun onImageClicked(file: File)
}