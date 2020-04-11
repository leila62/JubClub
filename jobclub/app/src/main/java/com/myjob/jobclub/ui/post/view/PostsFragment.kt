package com.myjob.jobclub.ui.post.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.myjob.jobclub.R
import com.myjob.jobclub.ui.post.model.PostEntity
import com.myjob.jobclub.ui.post.model.PostModel
import com.myjob.jobclub.ui.post.view.adapter.PostAdapterEvent
import com.myjob.jobclub.ui.post.view.adapter.PostsAdapter
import com.myjob.jobclub.ui.post.viewModel.PostViewModel
import com.myjob.jobclub.ui.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_posts.*

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
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PostsAdapter(object : PostAdapterEvent {
            override fun onPostItemCLicked(postEntity: PostEntity) {
                viewModel.selectedPostEntity = postEntity
                (activity as PostsActivity).showPostDetailFragment(postEntity)
            }
        })
        postsRecyclerView.adapter = adapter

        addFloatingButton.setOnClickListener {
            (activity as PostsActivity).showAddNewFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val model = PostModel()
        activity?.let { safeActivity ->
            viewModel = ViewModelProviders.of(safeActivity, PostModel.Factory(model)).get(PostViewModel::class.java)
        }
        viewModel.getPosts().observe(viewLifecycleOwner, Observer {
            updateData(it)
            (addFloatingButton as View).visibility = View.VISIBLE
        })
    }

    val lists = ArrayList<PostEntity>()

    private fun updateData(posts: PostEntity) {
        lists.add(posts)

        adapter.data =lists
    }
}