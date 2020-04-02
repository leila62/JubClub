package com.myjob.jobclub.ui.post.view.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myjob.jobclub.ui.post.model.PostEntity
import com.myjob.jobclub.ui.utils.BaseRecyclerViewAdapter

class PostsAdapter(val postAdapterEvent: PostAdapterEvent) : BaseRecyclerViewAdapter<PostEntity, RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        Log.i("ViewMODELLLLLL","${item} ---- position${position}")

        (holder as PostsViewHolder).onBind(item, postAdapterEvent)
    }
}


interface PostAdapterEvent {
    fun onPostItemCLicked(postEntity: PostEntity)
}