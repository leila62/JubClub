package com.myjob.jobclub.ui.post.view.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myjob.jobclub.R
import com.myjob.jobclub.ui.post.model.PostEntity
import kotlinx.android.synthetic.main.fragment_post_detail.*
import kotlinx.android.synthetic.main.item_post_layout.view.*

class PostsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post_layout, parent, false)) {

    val titleTextView = itemView.titleTextView
    val descriptionTextView = itemView.descriptionTextView
    val photoImageView = itemView.photoImageView

    fun onBind(postEntity: PostEntity, postAdapterEvent: PostAdapterEvent) {
        titleTextView.text = postEntity.title
        descriptionTextView.text = postEntity.description
        postEntity.photoUri?.let {
            Glide.with(itemView.context).load(it).into(photoImageView)
        }

        itemView.setOnClickListener {
            postAdapterEvent.onPostItemCLicked(postEntity)
        }
    }
}