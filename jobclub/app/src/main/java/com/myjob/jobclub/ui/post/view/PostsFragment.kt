package com.myjob.jobclub.ui.post.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.myjob.jobclub.R
import com.myjob.jobclub.ui.post.model.PostEntity
import com.myjob.jobclub.ui.post.model.PostModel
import com.myjob.jobclub.ui.post.view.adapter.PostAdapterEvent
import com.myjob.jobclub.ui.post.view.adapter.PostsAdapter
import com.myjob.jobclub.ui.post.viewModel.PostViewModel
import com.myjob.jobclub.ui.utils.BaseFragment
import kotlinx.android.synthetic.main.posts_fragment.*

class PostsFragment : BaseFragment() {
    private lateinit var viewModel: PostViewModel
    private lateinit var adapter: PostsAdapter

    companion object {
        private const val TAG = "PostsFragment"

        fun newInstance(): PostsFragment {
            return PostsFragment()
        }
    }

    override fun getFragmentTag(): String {
        return TAG
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.posts_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PostsAdapter(object : PostAdapterEvent {
            override fun onPostItemCLicked(postEntity: PostEntity) {
                (activity as PostsActivity).showPostDetailFragment(postEntity)
            }
        })
        postsRecyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val model = PostModel()
        viewModel = ViewModelProviders.of(this, PostModel.Factory(model)).get(PostViewModel::class.java)
        viewModel.getPosts().observe(viewLifecycleOwner, Observer {

            updateData(it)
        })
    }

    private fun updateData(posts: List<PostEntity>) {
        adapter.data = ArrayList(posts)
    }
}