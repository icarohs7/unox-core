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

package com.github.icarohs7.navigation.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.transaction
import com.github.icarohs7.navigation.NavigationModuleSettings
import com.github.icarohs7.navigation.NavigationModuleSettings.activityContainer
import com.github.icarohs7.navigation.NavigationModuleSettings.masterContainer

inline fun <reified T : Fragment> AppCompatActivity.loadFragment(
        destination: T,
        containerId: Int = masterContainer ?: activityContainer[this::class.simpleName] ?: 0
) {

    fragmentTransactionAnimated {
        replace(containerId, destination)
        addToBackStack("fragment")
    }
}

inline fun <reified T : Fragment> AppCompatActivity.loadFragmentWithoutBack(
        destination: T,
        containerId: Int = masterContainer ?: activityContainer[this::class.simpleName] ?: 0
) {

    fragmentTransactionAnimated { replace(containerId, destination) }
}

fun AppCompatActivity.fragmentTransactionAnimated(fn: FragmentTransaction.() -> Unit) {
    supportFragmentManager.transaction {
        setCustomAnimations(
                NavigationModuleSettings.enterAnim,
                NavigationModuleSettings.exitAnim,
                NavigationModuleSettings.popEnterAnim,
                NavigationModuleSettings.popExitAnim)
        fn()
    }
}