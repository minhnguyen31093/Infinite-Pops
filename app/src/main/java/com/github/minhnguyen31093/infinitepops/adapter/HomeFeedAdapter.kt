package com.github.minhnguyen31093.infinitepops.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.minhnguyen31093.infinitepops.R
import com.github.minhnguyen31093.infinitepops.base.BaseRecyclerAdapterLoadMore
import com.github.minhnguyen31093.infinitepops.base.BaseViewHolder
import com.github.minhnguyen31093.infinitepops.model.FeedItem
import kotlinx.android.synthetic.main.item_feed.*

class HomeFeedAdapter(recyclerView: RecyclerView, items: List<FeedItem>?) : BaseRecyclerAdapterLoadMore<FeedItem>(recyclerView, items) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<FeedItem> {
        return ViewHolder(R.layout.item_feed, parent)
    }

    inner class ViewHolder(layoutId: Int, parent: ViewGroup) : BaseViewHolder<FeedItem>(layoutId, parent) {
        override fun bind(item: FeedItem?, position: Int) {
            if (item != null) {
                if (!item.imageSrc.isNullOrEmpty()) {
                    ivItem.setImageUrl(item.imageSrc)
                }
                if (!item.text.isNullOrEmpty()) {
                    tvItem.text = item.text
                }
            }
        }
    }
}