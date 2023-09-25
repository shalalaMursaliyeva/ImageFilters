package com.example.imagefilter.dependencyinjection

import com.example.imagefilter.viewmodels.EditImageViewModel
import com.example.imagefilter.viewmodels.SavedImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { EditImageViewModel(editImageRepository = get()) }
    viewModel {SavedImagesViewModel(savedImageRepository = get())}
}