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

package com.github.icarohs7.unoxandroid.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import arrow.core.Try
import com.github.icarohs7.unoxandroid.extensions.coroutines.MainScope
import com.github.icarohs7.unoxandroid.extensions.coroutines.cancelCoroutineScope
import kotlinx.coroutines.CoroutineScope

/**
 * Base adapter based on data binding
 */
abstract class BaseBindingAdapter<T, DB : ViewDataBinding>(
        @LayoutRes val itemLayout: Int,
        diffCallback: DiffUtil.ItemCallback<T>?
) : ListAdapter<T, BaseBindingAdapter.BaseBindingViewHolder<DB>>(
        diffCallback ?: AllRefreshDiffCallback()
), CoroutineScope by MainScope() {
    /**
     * Function converting an list item to an actual view
     */
    abstract fun onBindItemToView(item: T, view: DB)

    /**
     * Creation of the viewholder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<DB> {
        val binding: DB = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                itemLayout,
                parent,
                false
        )

        return BaseBindingViewHolder(binding)
    }

    /**
     * Setup of the viewholder when going to be visible
     */
    override fun onBindViewHolder(holder: BaseBindingViewHolder<DB>, position: Int) {
        Try { onBindItemToView(getItem(position), holder.binding) }
    }

    /**
     * Cancel all coroutines when detached from the recycler view
     */
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        cancelCoroutineScope()
    }

    /**
     * Viewholder for the adapter
     */
    class BaseBindingViewHolder<DB : ViewDataBinding>(val binding: DB) : RecyclerView.ViewHolder(binding.root)

    /**
     * Default callback for lazy people, will just
     * use equals on the objects compared
     */
    class AllRefreshDiffCallback<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }
}