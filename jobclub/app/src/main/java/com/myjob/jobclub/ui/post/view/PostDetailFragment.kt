package com.myjob.jobclub.ui.post.view

import com.myjob.jobclub.ui.utils.BaseFragment

class PostDetailFragment : BaseFragment() {

    companion object {
        private const val TAG = "PostDetailFragment"
        fun newInstance(): PostDetailFragment {
            return PostDetailFragment()
        }
    }

    override fun getFragmentTag(): String {
        return TAG
    }
}