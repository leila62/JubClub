package com.myjob.jobclub.ui.user.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.myjob.jobclub.ui.user.viewModel.UserViewModel

class UserModel {
    companion object {
        private const val USERS = "users"
    }

    val db = Firebase.firestore

    fun fetchUser(): LiveData<RequestResult> {
        return MutableLiveData<RequestResult>().apply {
            db.collection(USERS)
                    .get()
                    .addOnSuccessListener { result ->
                        val obj = result.documents[0].data
                        val json = Gson().toJson(obj)
                        val user = Gson().fromJson(json, User::class.java)
                        //  RequestResult.onSuccess<T>(user)
                    }
                    .addOnFailureListener { exception ->
                        RequestResult.onFailure(exception)
                    }
        }
    }

    class Factory(private val model: UserModel) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = UserViewModel(model) as T
    }
}

sealed class RequestResult {
    data class onSuccess<T>(val data: T) : RequestResult()
    data class onFailure(val error: Exception) : RequestResult()
}