package com.myjob.jobclub.ui.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myjob.jobclub.R
import com.myjob.jobclub.ui.utils.BaseFragment

class ProfileFragment : BaseFragment() {

    companion object{
        private const val TAG = "ProfileFragment"

        fun newInstance():ProfileFragment{
            return ProfileFragment()
        }
    }
    override fun getFragmentTag(): String {
        return TAG
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}