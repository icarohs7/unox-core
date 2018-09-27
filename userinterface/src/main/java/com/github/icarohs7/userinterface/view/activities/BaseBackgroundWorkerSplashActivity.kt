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

package com.github.icarohs7.userinterface.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.icarohs7.animation.extensions.animateScaleIn
import com.github.icarohs7.core.extensions.ifTrue
import com.github.icarohs7.core.toplevel.NXBGPOOL
import com.github.icarohs7.core.toplevel.onUi
import com.github.icarohs7.core.toplevel.runAfterDelay
import com.github.icarohs7.userinterface.R
import com.github.icarohs7.userinterface.databinding.PartialCenterAndBottomConteinerBinding
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

/**
 * Base splash activity with support for background work
 * while showing the splash, with [T] being the type of
 * return of the task, you can also use Unit or anything
 * you want if not doing background work
 */
abstract class BaseBackgroundWorkerSplashActivity<T> : AppCompatActivity() {

    protected lateinit var root: PartialCenterAndBottomConteinerBinding
    private var backgroundTask: Deferred<T?> = async(NXBGPOOL) { null }

    /**
     * Called when the binding is set and waiting content
     */
    abstract fun onBindingCreated(binding: PartialCenterAndBottomConteinerBinding)

    /**
     * Called after the end of the heavy background operation
     * with its result
     */
    abstract fun changeToNextScreen(backgroundTaskResult: T?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindings()
        onBindingCreated(root)
        verifyIfWillDoBackgroundWork()
        runAnimations()
        waitBackgroundOperationsAndProceed()
    }

    /**
     * Called to check if there will be background work
     */
    private fun verifyIfWillDoBackgroundWork() {
        val willDo = confirmIfShouldDoBackgroundWorkBeforeStarting()
        willDo ifTrue {
            backgroundTask = startBackgroundOperations()
        }
    }

    /**
     * Called just before the background task start, if true
     * the background work will start
     */
    open fun confirmIfShouldDoBackgroundWorkBeforeStarting(): Boolean {
        return true
    }

    /**
     * Return the deferred heavy background operation in this
     * function, which will be awaited before going to the
     * next screen
     */
    open fun startBackgroundOperations(): Deferred<T?> {
        return async(NXBGPOOL) { null }
    }

    private fun setupBindings() {
        root = DataBindingUtil.setContentView(this, R.layout.partial_small_center_container)
    }

    /**
     * Animations being run by the splash, by default it's
     * an scaleIn with 600ms animation times
     */
    open fun runAnimations() {
        val content = root.centerContainer

        content.scaleX = 0f
        content.scaleY = 0f
        content.animateScaleIn(600L)
    }

    /**
     * Wait the background work to be done and then run the
     * function changeToNextScreen with the result
     */
    private fun waitBackgroundOperationsAndProceed() {
        runAfterDelay(2000) {
            afterAnimationTimeout()
            launch(NXBGPOOL) {
                val bgTaskResult = backgroundTask.await()
                onUi { changeToNextScreen(bgTaskResult) }
            }
        }
    }

    /**
     * Called 2 seconds after the splash image is shown
     */
    open fun afterAnimationTimeout() {
    }
}