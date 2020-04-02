package com.myjob.jobclub.ui.user.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.myjob.jobclub.ui.user.model.RequestResult
import com.myjob.jobclub.ui.user.model.User
import com.myjob.jobclub.ui.user.model.UserModel

class UserViewModel(val model: UserModel) : ViewModel() {

    fun getUsers(): LiveData<User> {
        return Transformations.switchMap(model.fetchUser()) { requestResult ->
            MutableLiveData<User>().apply {
                when (requestResult) {
                    is RequestResult.onSuccess<*> -> {

                        this.value = requestResult.data as User
                    }
                }
            }
        }
    }
}
