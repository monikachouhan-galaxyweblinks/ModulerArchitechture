package com.gwl.core

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun ImageView.load(str: String?) {
    Glide.with(this)
        .load(str)
        .apply(RequestOptions().dontTransform())
        .into(this)
}


@BindingAdapter("loadCircularImage")
fun ImageView.loadCircularImage(str: String?) {
    Glide.with(this)
        .load(str)
        .apply(
            RequestOptions.circleCropTransform()
                .placeholder(R.drawable.profile_placeholder)
        )
        .into(this)
}

@BindingAdapter("loadCircularDrawable")
fun ImageView.loadCircularDrawable(drawable: Drawable?) {
    Glide.with(this)
        .load(drawable)
        .apply(
            RequestOptions.circleCropTransform()
        )
        .into(this)
}

fun ImageView.unload() {
    Glide.with(this).clear(this)
}

fun glideRequestOption(): RequestOptions = RequestOptions()
    .centerCrop()
/* .placeholder(R.color.pick_placeholder)
 .error(R.color.pick_placeholder)*/
