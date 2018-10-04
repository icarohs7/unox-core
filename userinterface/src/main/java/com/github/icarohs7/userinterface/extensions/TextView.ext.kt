package com.github.icarohs7.userinterface.extensions

import android.widget.TextView
import androidx.annotation.DrawableRes

/**
 * Define the left drawable of the text view
 */
fun TextView?.setDrawableLeft(@DrawableRes resource: Int) {
    this?.setCompoundDrawablesRelativeWithIntrinsicBounds(resource.drawableByResourceId(context), null, null, null)
}

/**
 * Define the right drawable of the text view
 */
fun TextView?.setDrawableRight(@DrawableRes resource: Int) {
    this?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, resource.drawableByResourceId(context), null)
}

/**
 * Define the top drawable of the text view
 */
fun TextView?.setDrawableTop(@DrawableRes resource: Int) {
    this?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, resource.drawableByResourceId(context), null, null)
}

/**
 * Define the bottom drawable of the text view
 */
fun TextView?.setDrawableBottom(@DrawableRes resource: Int) {
    this?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, resource.drawableByResourceId(context))
}