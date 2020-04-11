package com.myjob.jobclub.ui.post

import android.graphics.Bitmap
import com.myjob.jobclub.ui.post.model.PostEntity
import java.io.File

interface PostContract {

    interface IView {
        fun showPostsFragment()
        fun showPostDetailFragment(postEntity: PostEntity)
        fun showAddNewFragment()
        fun showProfileFragment()
        fun showLoginFragment()
    }

    interface ICamera{
        fun cameraClicked(photoCapturedListener: PhotoCapturedListener)
    }


    interface PhotoCapturedListener {
        fun onPhotoCaptured(imageFile: File)
    }
}