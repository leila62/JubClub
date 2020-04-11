package com.myjob.jobclub.ui.post.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.myjob.jobclub.R
import com.myjob.jobclub.ui.post.PostContract
import com.myjob.jobclub.ui.post.model.PostEntity
import com.myjob.jobclub.ui.post.model.PostModel
import com.myjob.jobclub.ui.post.viewModel.PostViewModel
import com.myjob.jobclub.ui.user.model.RequestResult
import com.myjob.jobclub.ui.utils.BaseFragment
import com.myjob.jobclub.ui.utils.setScaledBitmap
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.fragment_add_new_post.*
import java.io.File


class AddNewPostFragment : BaseFragment(), PostContract.PhotoCapturedListener {
    lateinit var toolbar: Toolbar
    private lateinit var viewModel: PostViewModel
    private var postEntity = PostEntity()
    private var photoFile: File? = null


    companion object {
        const val TAG = "AddNewPostFragment"
        const val TAKE_PICTURE = 1

        fun newInstance(): AddNewPostFragment {
            return AddNewPostFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_new_post, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        activity?.toolbar?.let {
            toolbar = it
        }
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        addPostTitleEditText.setTextWatcher {
            postEntity.title = it
        }

        addPostDescriptionEditText.setTextWatcher {
            postEntity.description = it
        }
        addPostPhoneEditText.setTextWatcher {
            postEntity.phone = it
        }

        photoContainer.setOnClickListener {
            (activity as PostsActivity).cameraClicked(this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { safeActivity ->
            viewModel = ViewModelProviders.of(safeActivity, PostModel.Factory(PostModel())).get(PostViewModel::class.java)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.findItem(R.id.profile)?.isVisible = false
        menu?.findItem(R.id.add_new_post)?.isVisible = true
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.add_new_post) {
            viewModel.addNewPost(postEntity, photoFile).observe(viewLifecycleOwner, Observer { result ->
                when(result){
                    is RequestResult.onSuccess<*>->{
                        (activity as PostsActivity).showPostsFragment()
                    }
                    is RequestResult.onFailure->{}
                }
            })
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun getFragmentTag(): String {
        return TAG
    }

    override fun onPhotoCaptured(imageFile: File) {
        photoFile = imageFile
        photoImageView.setImageBitmap(setScaledBitmap(imageFile.absolutePath, photoImageView))
    }
}

fun EditText.setTextWatcher(listener: ((inputText: String?) -> Unit)? = null) {

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            listener?.invoke(s.toString())
        }
    }

    this.addTextChangedListener(textWatcher)

}