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

package com.github.icarohs7.telephony.providers

import android.Manifest
import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.github.icarohs7.telephony.TelephonyModule
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import org.jetbrains.anko.longToast
import java.util.ArrayList

internal class PhoneCallProviderImpl : TelephonyModule.PhoneCallProvider {

    override fun callNumber(context: Context, phoneNumber: String, askingMessage: String, onDenyMessage: String) {
        val callback = permissionHandler(context, phoneNumber, onDenyMessage)

        Permissions.check(context, Manifest.permission.CALL_PHONE, askingMessage, callback)
    }

    private fun permissionHandler(context: Context, phoneNumber: String,
                                  onDenyMessage: String): PermissionHandler {
        return object : PermissionHandler() {
            override fun onGranted() {
                makeCall(context, phoneNumber)
            }

            override fun onDenied(context: Context, deniedPermissions: ArrayList<String>) {
                context.longToast(onDenyMessage)
            }
        }
    }

    private fun makeCall(context: Context, phoneNumber: String) = context.apply {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = "tel:$phoneNumber".toUri()

        if (hasPermissionToCall()) startActivity(callIntent)
    }

    private fun Context.hasPermissionToCall() =
            (ActivityCompat.checkSelfPermission(this, permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
}
