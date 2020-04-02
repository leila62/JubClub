package com.myjob.jobclub.ui.post.view

import android.os.Bundle
import com.myjob.jobclub.R
import com.myjob.jobclub.ui.post.PostContract
import com.myjob.jobclub.ui.post.model.PostEntity
import com.myjob.jobclub.ui.utils.BaseActivity

class PostsActivity : BaseActivity(), PostContract.IView {
    companion object {
        private const val TAG = "PostsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.posts_activity)
        if (savedInstanceState == null) {
            showFragment(PostsFragment.newInstance(), PostsFragment().getFragmentTag())
        }
    }

    override fun showPostDetailFragment(postEntity: PostEntity) {
        showFragment(PostDetailFragment.newInstance(), PostDetailFragment().getFragmentTag())
    }
}
