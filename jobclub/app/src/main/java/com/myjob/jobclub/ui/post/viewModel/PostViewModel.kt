package com.myjob.jobclub.ui.post.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.myjob.jobclub.ui.post.model.PostEntity
import com.myjob.jobclub.ui.post.model.PostModel
import com.myjob.jobclub.ui.user.model.RequestResult
import java.io.File

class PostViewModel(val model: PostModel) : ViewModel() {

    var selectedPostEntity = PostEntity()

    fun getPosts(): LiveData<PostEntity> {
        return Transformations.switchMap(model.fetchPosts()) { requestResult ->
            MutableLiveData<PostEntity>().apply {
                when (requestResult) {
                    is RequestResult.onSuccess<*> -> {
                        this.value = requestResult.data as PostEntity
                    }
                }
            }
        }
    }

    fun addNewPost(postEntity: PostEntity, imageFile: File?): LiveData<RequestResult> {
        return Transformations.switchMap(model.addNewPost(postEntity, imageFile)) { requestResult ->
            MutableLiveData<RequestResult>().apply {
                when (requestResult) {
                    is RequestResult.onSuccess<*> -> {
                        this.value = RequestResult.onSuccess(requestResult.data)
                    }
                    is RequestResult.onFailure->{
                        this.value = RequestResult.onFailure(requestResult.error)
                    }
                }
            }
        }
    }

    fun getPhotoUrl(photoUrl: String): LiveData<RequestResult> {
        return Transformations.switchMap(model.getPhotoUrl(photoUrl)) { requestResult ->
            MutableLiveData<RequestResult>().apply {
                when (requestResult) {
                    is RequestResult.onSuccess<*> -> {
                        this.value = RequestResult.onSuccess(requestResult.data)
                    }
                    is RequestResult.onFailure->{
                        this.value = RequestResult.onFailure(requestResult.error)
                    }
                }
            }
        }
    }
}