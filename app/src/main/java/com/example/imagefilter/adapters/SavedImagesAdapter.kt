package com.example.imagefilter.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagefilter.databinding.ItemContainerFilterBinding
import com.example.imagefilter.databinding.ItemContainrSavedImagesBinding
import com.example.imagefilter.listener.SavedImagesListener
import java.io.File

class SavedImagesAdapter(
    private val savedImages: List<Pair<File, Bitmap>>,
    private val savedImagesListener: SavedImagesListener,
) :
    RecyclerView.Adapter<SavedImagesAdapter.SavedImageHolder>() {

    inner class SavedImageHolder(val binding: ItemContainrSavedImagesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedImageHolder {
        val binding = ItemContainrSavedImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SavedImageHolder(binding)
    }

    override fun getItemCount() = savedImages.size

    override fun onBindViewHolder(holder: SavedImageHolder, position: Int) {
        with(holder) {
            with(savedImages[position]) {
                binding.savedImage.setImageBitmap(second)
                binding.savedImage.setOnClickListener {
                    savedImagesListener.onImageClicked(first)
                }
            }
        }
    }
}