package com.myjob.jobclub.ui.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myjob.jobclub.R
import com.myjob.jobclub.ui.utils.BaseFragment

class LoginFragment : BaseFragment() {

    companion object {
        private const val TAG = "LoginFragment"

        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun getFragmentTag(): String {
        return TAG
    }
}