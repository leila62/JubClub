package com.myjob.jobclub.ui.post.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.myjob.jobclub.ui.post.viewModel.PostViewModel
import com.myjob.jobclub.ui.user.model.RequestResult

class PostModel {
    companion object {
        private const val POSTS = "posts"
    }

    val db = Firebase.firestore

    fun fetchPosts(): LiveData<RequestResult> {
        return MutableLiveData<RequestResult>().apply {
            db.collection(POSTS)
                    .get()
                    .addOnSuccessListener { result ->
                        val posts: ArrayList<PostEntity> = arrayListOf()

                        for (document in result) {
                            val post = document.toObject(PostEntity::class.java)
                            posts.add(post)
                        }
                        this.value = RequestResult.onSuccess(posts)
                    }
                    .addOnFailureListener { exception ->
                        this.value = RequestResult.onFailure(exception)
                    }
        }
    }

    private fun writeDataOnFirestore() {

        val post = HashMap<String, Any>()
        post["title"] = "REZA"
        post["description"] = "NETWORK Admin"
        db.collection(POSTS).document("posts_list")
                .set(post)
                .addOnSuccessListener {
                    Log.d("SETTING DATAAA", "DocumentSnapshot successfully written!")

                }.addOnFailureListener { e ->
                    Log.e("ERRROR", "Error writing document", e)
                }
    }

    class Factory(private val model: PostModel) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = PostViewModel(model) as T
    }
}