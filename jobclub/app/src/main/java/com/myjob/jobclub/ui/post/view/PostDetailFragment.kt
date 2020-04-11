package com.myjob.jobclub.ui.post.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.myjob.jobclub.R
import com.myjob.jobclub.ui.post.model.PostEntity
import com.myjob.jobclub.ui.post.model.PostModel
import com.myjob.jobclub.ui.post.viewModel.PostViewModel
import com.myjob.jobclub.ui.user.model.RequestResult
import com.myjob.jobclub.ui.utils.BaseFragment
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.fragment_post_detail.*


class PostDetailFragment : BaseFragment() {

    private lateinit var viewModel: PostViewModel
    lateinit var postEntity: PostEntity
    lateinit var toolbar: Toolbar

    companion object {
        private const val TAG = "PostDetailFragment"
        private const val POST_ENTITY_EXTRA = "post_entity_extra"
        fun newInstance(): PostDetailFragment {
            return PostDetailFragment()
        }
    }

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar?.let {
            toolbar = it
        }
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { safeActivity ->
            viewModel = ViewModelProviders.of(safeActivity, PostModel.Factory(PostModel())).get(PostViewModel::class.java)
        }
        postEntity = viewModel.selectedPostEntity
        postEntity.photo?.let {
            viewModel.getPhotoUrl(it).observe(viewLifecycleOwner, Observer {
                when (it) {
                    is RequestResult.onSuccess<*> -> {
                        Glide.with(this).load(it.data).into(postPhotoImageView)
                    }
                }
            })
        }
        updateView(postEntity)
    }

    private fun updateView(postEntity: PostEntity) {
        detailTitleTextView.text = postEntity.title
        detailDescriptionTextView.text = postEntity.description
    }
}