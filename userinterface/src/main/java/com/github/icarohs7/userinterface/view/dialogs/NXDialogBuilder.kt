/*
 * MIT License
 *
 * Copyright (c) 2018 Icaro R D Temponi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.icarohs7.userinterface.view.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import com.github.icarohs7.core.extensions.CHAIN
import com.github.icarohs7.templates.extensions.inflateBinding
import com.github.icarohs7.userinterface.R
import com.github.icarohs7.userinterface.databinding.DialogNxBinding

@Suppress("MemberVisibilityCanBePrivate")
class NXDialogBuilder(private val context: Context) {
    var title: String = "Info"
    var message: String = "Lorem Ipsum"
    var icon: Drawable? = null
    var backgroundResource = android.R.color.white
    var buttonText = "Continue"
    var buttonTextColorResource = android.R.color.black
    var buttonColorResource = android.R.color.white
    var buttonCallback = Runnable {}
    var dismissCallback = Runnable {}
    var customView: View? = null
    val dialogView = context.inflateBinding<DialogNxBinding>(R.layout.dialog_nx)

    fun build() {
        NXDialog(
                context = context,
                title = title,
                message = message,
                icon = icon,
                backgroundColorResource = backgroundResource,
                buttonText = buttonText,
                buttonTextColorResource = buttonTextColorResource,
                buttonColorResource = buttonColorResource,
                buttonCallback = buttonCallback,
                dismissCallback = dismissCallback,
                customView = customView,
                binding = dialogView) CHAIN Dialog::show
    }
}