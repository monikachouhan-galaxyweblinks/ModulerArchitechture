package com.gwl.core

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


@BindingAdapter("imageUrl")
fun ImageView.load(str: String?) {
    Glide.with(this)
        .load(str)
        .apply(RequestOptions().dontTransform())
        .into(this)
}

@BindingAdapter("crouselImageUrl")
fun ImageView.loadCaroselImage(str: String?) {
    try {
        Glide.with(this)
            .asBitmap()
            .load(str)
            .apply(RequestOptions().dontTransform())
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return true
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (resource != null) {
                        val p: Palette = Palette.from(resource).generate()
                        // Use generated instance
                        this@loadCaroselImage.setBackgroundColor(
                             p.getDarkVibrantColor(
                                 ContextCompat.getColor(
                                     context, R.color.default_shimmer_color
                                 )
                             )
                        )
                        this@loadCaroselImage.setImageBitmap(resource)
                    }
                    return true
                }

            }).into(this)
    } catch (e: Exception) {
        Log.d("loadCaroselImage", "loadCaroselImage exception  $e")

    }
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
