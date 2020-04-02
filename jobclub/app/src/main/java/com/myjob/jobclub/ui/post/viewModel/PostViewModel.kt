package com.myjob.jobclub.ui.post.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.myjob.jobclub.ui.post.model.PostEntity
import com.myjob.jobclub.ui.post.model.PostModel
import com.myjob.jobclub.ui.user.model.RequestResult

class PostViewModel(val model: PostModel) : ViewModel() {

    fun getPosts(): LiveData<List<PostEntity>> {
        return Transformations.switchMap(model.fetchPosts()) { requestResult ->
            MutableLiveData<List<PostEntity>>().apply {
                when (requestResult) {
                    is RequestResult.onSuccess<*> -> {
                        this.value = requestResult.data as List<PostEntity>
                    }
                }
            }
        }
    }
}