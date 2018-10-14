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

package com.github.icarohs7.telephony

import android.content.Context
import com.github.icarohs7.telephony.providers.PhoneCallProviderImpl

interface TelephonyModule {

    interface PhoneCallProvider {
        /**
         * Make a phone calling to the parameterized phone number and, when needing to
         * as permission, uses the asking and on deny messages when asking the permission
         * and when the permission is denied, respectively
         */
        fun callNumber(context: Context, phoneNumber: String, askingMessage: String, onDenyMessage: String)

        companion object {
            /**
             * Return an instance of the provider
             */
            fun get(): TelephonyModule.PhoneCallProvider = PhoneCallProviderImpl()
        }
    }
}