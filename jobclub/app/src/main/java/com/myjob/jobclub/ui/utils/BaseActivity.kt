package com.myjob.jobclub.ui.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.myjob.jobclub.R

abstract class BaseActivity : AppCompatActivity() {
    protected open val fragmentContainerId: Int = R.id.container

    protected fun showFragment(fragment: Fragment, tag: String?, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction().apply {
            if (addToBackStack) {
                addToBackStack(tag)
            }
        }.replace(fragmentContainerId, fragment, tag).commit()
    }
}