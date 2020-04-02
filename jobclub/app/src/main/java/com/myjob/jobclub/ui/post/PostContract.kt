package com.myjob.jobclub.ui.post

import com.myjob.jobclub.ui.post.model.PostEntity

interface PostContract {

    interface IView {
        fun showPostDetailFragment(postEntity: PostEntity)
    }
}