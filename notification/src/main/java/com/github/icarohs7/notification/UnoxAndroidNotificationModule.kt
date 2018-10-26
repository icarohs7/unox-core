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

package com.github.icarohs7.notification

import android.app.PendingIntent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.github.icarohs7.notification.providers.NotificationProviderImpl
import kotlin.reflect.KClass

interface UnoxAndroidNotificationModule {

    interface NotificationProvider {

        /**
         * Show a notification to the user
         */
        fun emitNotification(
                title: String,
                message: String,
                iconResource: Int,
                bigMessage: String,
                onClickPendingIntent: PendingIntent
        )

        /**
         * Use a builder to show a notification to the user
         */
        fun buildNotification(bigMessage: String = "", fn: NotificationCompat.Builder.() -> Unit)

        /**
         * Show a notification to the user
         */
        fun emitNotification(
                title: String,
                message: String,
                iconResource: Int,
                bigMessage: String,
                destinationActivity: KClass<out AppCompatActivity>
        )

        companion object {
            /**
             * Return an instance of the provider
             */
            fun get(context: Context, channelId: String = "standardchannelid"): NotificationProvider {
                return NotificationProviderImpl(context, channelId)
            }
        }
    }

}