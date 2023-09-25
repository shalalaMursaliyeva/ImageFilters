package com.example.imagefilter.listener

import com.example.imagefilter.model.ImageFilter

interface ImageFilterListener {
    fun onFileSelected(imageFilter: ImageFilter)
}