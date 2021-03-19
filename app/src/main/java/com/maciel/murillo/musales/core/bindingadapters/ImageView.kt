package com.maciel.murillo.musales.core.bindingadapters

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.maciel.murillo.musales.R

@BindingAdapter("app:imageUrl")
fun imageUrl(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(R.drawable.standard)
        .into(view)
}

@BindingAdapter("app:imagePath")
fun imagePath(imageView: ImageView, path: String?) {
    if (path.isNullOrBlank()) {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.standard))
    } else {
        return try {
            imageView.setImageURI(path.toUri())
        } catch (e: Throwable) {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.standard))
        }
    }
}

@BindingAdapter("app:imageDrawableId")
fun imageDrawableId(view: ImageView, drawableId: Int?) {
    if (drawableId != null && drawableId != 0) {
        view.setImageDrawable(AppCompatResources.getDrawable(view.context, drawableId))
    }
}