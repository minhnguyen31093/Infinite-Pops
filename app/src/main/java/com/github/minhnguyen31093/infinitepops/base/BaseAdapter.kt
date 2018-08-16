package com.github.minhnguyen31093.infinitepops.base

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>> {

    private var items: ArrayList<T>? = null
    protected var itemClickListener: OnItemClickListener<T>? = null

    protected val onClickListener = View.OnClickListener { view ->
        if (view != null && view.tag != null && view.tag is RecyclerView.ViewHolder && itemClickListener != null) {
            val viewHolder = view.tag as RecyclerView.ViewHolder
            itemClickListener!!.onClick(view, viewHolder.adapterPosition, getItem(viewHolder.adapterPosition))
        }
    }

    constructor(items: List<T>?) : this(if (items != null) ArrayList(items) else null)

    constructor(items: ArrayList<T>?) {
        this.items = items
    }

    constructor(items: List<T>?, onItemClickListener: OnItemClickListener<T>?) : this(if (items != null) ArrayList(items) else null, onItemClickListener)

    constructor(items: ArrayList<T>?, onItemClickListener: OnItemClickListener<T>?) {
        this.items = items
        this.itemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.itemView.tag = holder
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(onClickListener)
        }
        holder.bind(items!![position], position)
    }

    override fun getItemCount(): Int {
        return if (items != null) {
            items!!.size
        } else {
            0
        }
    }

    fun getView(@LayoutRes layoutId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }

    fun clear() {
        items?.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T? {
        return if (items != null && position < items!!.size) items!![position] else null
    }

    fun setItems(list: ArrayList<T>) {
        items = list
    }

    fun getItems(): ArrayList<T>? {
        return items
    }

    fun getItemsClone(): List<T>? {
        return if (items != null) ArrayList(items!!) else null
    }

    fun remove(index: Int) {
        var position = index
        if (position > 0 && itemCount == 1) {
            position = 0
        }
        items!!.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(if (position > 0) position - 1 else position, itemCount)
    }

    fun remove(item: T) {
        remove(items!!.indexOf(item))
    }

    fun removeRange(index: Int) {
        val count = itemCount
        val lastItemIndex = count - 1
        for (i in lastItemIndex downTo index) {
            items!!.removeAt(i)
        }
        notifyItemRangeRemoved(index, count)
    }

    fun update(position: Int, item: T) {
        items!![position] = item
        notifyItemChanged(position)
    }

    open fun insert(item: T) {
        if (item != null) {
            if (items == null) {
                items = ArrayList()
            }
            items!!.add(item)
            notifyItemInserted(items!!.size - 1)
        }
    }

    open fun insert(newItems: List<T>?) {
        if (newItems != null) {
            if (items == null) {
                items = ArrayList()
            }
            items!!.addAll(newItems)
            notifyItemRangeInserted(items!!.size - newItems.size, newItems.size)
        }
    }

    open fun insertNew(newItems: List<T>?) {
        if (newItems != null) {
            if (items == null) {
                items = ArrayList()
            } else {
                items!!.clear()
            }
            items!!.addAll(newItems)
            notifyDataSetChanged()
        }
    }

    fun insertTop(item: T) {
        if (item != null) {
            if (items == null) {
                items = ArrayList()
            }
            items!!.add(0, item)
            notifyDataSetChanged()
        }
    }

    fun insertTop(list: List<T>?) {
        if (list != null) {
            if (items == null) {
                items = ArrayList()
            }
            items!!.addAll(0, list)
            notifyDataSetChanged()
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.itemClickListener = onItemClickListener
    }

    interface OnItemClickListener<T> {
        fun onClick(view: View?, position: Int, item: T?)
    }
}