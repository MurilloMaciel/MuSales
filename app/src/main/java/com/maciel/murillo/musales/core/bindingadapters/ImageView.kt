package com.maciel.murillo.musales.core.bindingadapters

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
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

@BindingAdapter("app:imageDrawableId")
fun imageDrawableId(view: ImageView, drawableId: Int?) {
    if (drawableId != null && drawableId != 0) {
        view.setImageDrawable(AppCompatResources.getDrawable(view.context, drawableId))
    }
}