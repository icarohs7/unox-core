package com.github.icarohs7.userinterface.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Load a image view from a path
 */
@JvmOverloads
fun ImageView?.loadImage(picturePath: String?, placeholder: Drawable? = null, error: Drawable? = null) {
    this?.run {
        Glide.with(context)
                .load(picturePath)
                .also { _ ->
                    val options = RequestOptions().centerCrop()
                    placeholder?.let(options::placeholder)
                    error?.let(options::error)
                }
                .into(this)
    }
}

/**
 * Load a image drawable onto a ImageView from a drawable resource
 */
fun ImageView?.setImageDrawable(@DrawableRes resource: Int) {
    this?.setImageDrawable(resource.drawableByResourceId(context))
}