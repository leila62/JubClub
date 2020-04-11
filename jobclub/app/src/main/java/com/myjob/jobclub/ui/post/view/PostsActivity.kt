package com.myjob.jobclub.ui.post.view

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.myjob.jobclub.R
import com.myjob.jobclub.ui.post.PostContract
import com.myjob.jobclub.ui.post.model.PostEntity
import com.myjob.jobclub.ui.profile.view.LoginFragment
import com.myjob.jobclub.ui.profile.view.ProfileFragment
import com.myjob.jobclub.ui.utils.BaseActivity
import com.myjob.jobclub.ui.utils.createImageFile
import com.myjob.jobclub.ui.utils.hasNoPermissions
import com.myjob.jobclub.ui.utils.requestPermission
import java.io.File
import java.io.IOException

class PostsActivity : BaseActivity(), PostContract.IView, PostContract.ICamera {
    var toolbar: Toolbar? = null
    lateinit var imageFile: File

    lateinit var listener: PostContract.PhotoCapturedListener

    companion object {
        private const val TAG = "PostsActivity"
        private const val FILE_PROVIDER = ".provider"
        val CAMERA_REQUEST_CODE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        (toolbar as Toolbar).setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            showPostsFragment()
        }

        if (hasNoPermissions(this)) {
            requestPermission(this)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            toolbar?.setNavigationIcon(R.drawable.icon_arrow_left_white)
        } else {
            toolbar?.navigationIcon = null

        }
        super.onBackPressed()
    }

    override fun showPostsFragment() {
        showFragment(PostsFragment.newInstance(), PostsFragment().getFragmentTag())
    }


    override fun showPostDetailFragment(postEntity: PostEntity) {
        showFragment(PostDetailFragment.newInstance(), PostDetailFragment().getFragmentTag())
    }

    override fun showAddNewFragment() {
        showFragment(AddNewPostFragment.newInstance(), AddNewPostFragment().getFragmentTag())
    }

    override fun showProfileFragment() {
        showFragment(ProfileFragment.newInstance(), ProfileFragment().getFragmentTag())
    }

    override fun showLoginFragment() {
        showFragment(LoginFragment.newInstance(), LoginFragment().getFragmentTag())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_post, menu)
        menu?.findItem(R.id.profile)?.icon?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.profile) {
            showProfileFragment()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun cameraClicked(photoCapturedListener: PostContract.PhotoCapturedListener) {
        listener = photoCapturedListener
        launchCamera()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    listener.onPhotoCaptured(imageFile)
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun launchCamera() {
        try {
            imageFile = createImageFile(this)
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (callCameraIntent.resolveActivity(packageManager) != null) {
                val authorities = packageName + FILE_PROVIDER
                val imageUri = FileProvider.getUriForFile(this, authorities, imageFile)
                callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Could not create file!", Toast.LENGTH_SHORT).show()
        }
    }
}

