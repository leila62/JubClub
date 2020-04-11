package com.myjob.jobclub.ui.post.model

import androidx.lifecycle.*
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.myjob.jobclub.ui.post.viewModel.PostViewModel
import com.myjob.jobclub.ui.user.model.RequestResult
import java.io.File
import java.io.FileInputStream
import java.util.*

class PostModel {
    companion object {
        private const val POSTS = "posts"
    }

    val db = Firebase.firestore

    fun fetchPosts(): LiveData<RequestResult> {
        val storageRef = Firebase.storage.reference

        return MutableLiveData<RequestResult>().apply {
            db.collection(POSTS)
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            val post = document.toObject(PostEntity::class.java)
                            post.photo?.let { photo ->
                                storageRef.child(photo).downloadUrl
                                        .addOnSuccessListener {
                                            post.photoUri = it
                                        }
                            }
                            this.value = RequestResult.onSuccess(post)
                        }
                    }
                    .addOnFailureListener { exception ->
                        this.value = RequestResult.onFailure(exception)
                    }
        }
    }


    fun getPhotoUrl(photoUrl: String): LiveData<RequestResult> {
        val storageRef = Firebase.storage.reference

        return MutableLiveData<RequestResult>().apply {
            storageRef.child(photoUrl).downloadUrl
                    .addOnSuccessListener {
                        this.value = RequestResult.onSuccess(it)
                    }
                    .addOnFailureListener {
                        this.value = RequestResult.onFailure(it)
                    }
        }
    }


    fun addNewPost(postEntity: PostEntity, imageFile: File?): LiveData<RequestResult> {
        val timeStamp = FieldValue.serverTimestamp().toString()
        postEntity.createdDate = timeStamp
        postEntity.id = timeStamp

        return Transformations.switchMap(uploadPhoto(imageFile, "${timeStamp}/${imageFile?.name}")) { requestResult ->
            MutableLiveData<RequestResult>().apply {
                when (requestResult) {
                    is RequestResult.onSuccess<*> -> {
                        postEntity.photo = requestResult.data as String
                        db.collection(POSTS).document(timeStamp)
                                .set(postEntity)
                                .addOnSuccessListener {
                                    this.value = RequestResult.onSuccess("Added Successfully")

                                }.addOnFailureListener { exception ->
                                    this.value = RequestResult.onFailure(exception)
                                }
                    }
                }
            }
        }
    }


    private fun uploadPhoto(imageFile: File?, photoUrl: String): LiveData<RequestResult> {

        val storageRef = Firebase.storage.reference
        return MutableLiveData<RequestResult>().apply {
            imageFile?.let {
                val mountainsRef = storageRef.child(it.name)
                val mountainImagesRef = storageRef.child(photoUrl)

                mountainsRef.name == mountainImagesRef.name
                mountainsRef.path == mountainImagesRef.path
                val stream = FileInputStream(imageFile)
                val uploadTask = mountainsRef.putStream(stream)

                uploadTask.addOnFailureListener {
                    this.value = RequestResult.onFailure(it)
                }.addOnSuccessListener {
                    this.value = RequestResult.onSuccess(it.metadata?.path)
                }
            }
        }
    }


    class Factory(private val model: PostModel) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = PostViewModel(model) as T
    }
}