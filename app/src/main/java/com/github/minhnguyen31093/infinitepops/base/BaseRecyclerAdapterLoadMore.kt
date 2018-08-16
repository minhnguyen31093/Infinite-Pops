package com.github.minhnguyen31093.infinitepops.base

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.github.minhnguyen31093.infinitepops.R
import kotlinx.android.synthetic.main.item_loadmore.*
import java.util.*

/*
 *
 * @param <T> Type of items this recycler view can display.
 */
typealias OnLoadMore = () -> Unit

abstract class BaseRecyclerAdapterLoadMore<T> : BaseAdapter<T> {

    private var recyclerView: RecyclerView
    private val visibleThreshold = 2
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0

    var isLoading: Boolean = false
    var isCompleted: Boolean = false
        set(value) {
            field = value
            if (value) {
                val position = itemCount - 1
                if (getItemViewType(position) == TYPE_LOAD_MORE) {
                    notifyItemRemoved(position)
                } else if (getItemViewType(itemCount) == TYPE_LOAD_MORE) {
                    try {
                        notifyItemRemoved(itemCount)
                    } catch (e: IndexOutOfBoundsException) {
                        Log.e("Error", e.message)
                    }
                }
            }
        }

    private var onLoadMore: OnLoadMore? = null

    constructor(recyclerView: RecyclerView, items: List<T>?) : this(recyclerView, if (items != null) ArrayList(items) else null)

    constructor(recyclerView: RecyclerView, items: List<T>?, onItemClickListener: BaseAdapter.OnItemClickListener<T>?) : this(recyclerView, if (items != null) ArrayList(items) else null, onItemClickListener)

    constructor(recyclerView: RecyclerView, items: ArrayList<T>?) : super(items) {
        this.recyclerView = recyclerView
    }

    constructor(recyclerView: RecyclerView, items: ArrayList<T>?, onItemClickListener: BaseAdapter.OnItemClickListener<T>?) : super(items, onItemClickListener) {
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return if (viewType == TYPE_LOAD_MORE) {
            LoadMoreViewHolder(R.layout.item_loadmore, parent)
        } else {
            onCreateHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        if (holder is BaseRecyclerAdapterLoadMore<*>.LoadMoreViewHolder<*>) {
            holder.bind(null, 0)
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    override fun getItemCount(): Int {
        val items = getItems()
        return if (items == null) 0 else items.size + if (isCompleted) 0 else 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= getItems()!!.size) TYPE_LOAD_MORE else TYPE_ITEM
    }

    abstract fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T>


    fun getRealCount(): Int {
        val items = getItems()
        return items?.size ?: 0
    }

    override fun insert(newItems: List<T>?) {
        super.insert(newItems)
        isLoading = false
    }

    fun onLoadMore(onListener: OnLoadMore) {
        this.onLoadMore = onListener
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                try {
                    val mLayoutManager = recyclerView.layoutManager
                    if (mLayoutManager != null) {
                        totalItemCount = mLayoutManager.itemCount
                        when (mLayoutManager) {
                            is StaggeredGridLayoutManager -> lastVisibleItem = getLastVisibleItem(mLayoutManager.findLastVisibleItemPositions(null))
                            is GridLayoutManager -> lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
                            is LinearLayoutManager -> lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
                        }
                        if (onLoadMore != null && !isCompleted && !isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                            isLoading = true
                            onLoadMore!!.invoke()
                        }
                    }
                } catch (e: IllegalStateException) {
                    Log.e("Error: ", e.message)
                }
            }
        })
    }

    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    private inner class LoadMoreViewHolder<T>(layoutId: Int, parent: ViewGroup) : BaseViewHolder<T>(layoutId, parent) {

        override fun bind(item: T?, position: Int) {
            lnlLoad.visibility = if (isCompleted) View.GONE else View.VISIBLE
        }
    }

    companion object {

        const val TYPE_LOAD_MORE = 222
        const val TYPE_ITEM = 333
    }
}