package com.myjob.jobclub.ui.utils

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun getFragmentTag(): String
}