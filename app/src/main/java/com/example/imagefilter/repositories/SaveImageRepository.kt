package com.example.imagefilter.repositories

import android.graphics.Bitmap
import java.io.File

interface SaveImageRepository {
    suspend fun loadSavedImages(): List<Pair<File, Bitmap>>?
}