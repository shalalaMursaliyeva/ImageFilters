package com.example.imagefilter.dependencyinjection

import com.example.imagefilter.repositories.EditImageRepoImpl
import com.example.imagefilter.repositories.EditImageRepository
import com.example.imagefilter.repositories.SaveImageRepository
import com.example.imagefilter.repositories.SavedImageRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory <EditImageRepository>{EditImageRepoImpl(androidContext())  }
    factory <SaveImageRepository>{SavedImageRepositoryImpl(androidContext())  }
}