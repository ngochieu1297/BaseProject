package com.example.baseproject.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

@BindingAdapter("imageUrl")
fun ImageView.loadImage(imageUrl: String) {
    this.load(imageUrl) {
        crossfade(true)
        transformations(CircleCropTransformation())
    }
}
