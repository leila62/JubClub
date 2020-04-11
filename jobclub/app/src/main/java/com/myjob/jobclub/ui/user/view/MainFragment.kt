package com.myjob.jobclub.ui.user.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.myjob.jobclub.R
import com.myjob.jobclub.ui.user.model.UserModel
import com.myjob.jobclub.ui.user.viewModel.UserViewModel
import com.myjob.jobclub.ui.utils.BaseFragment

class MainFragment : BaseFragment() {

    companion object {

        fun newInstance() = MainFragment()
         private const val TAG = "MainFragment"
    }

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val db = Firebase.firestore


        val user = hashMapOf(
                "name" to "leila",
                "userName" to "fa",
                "address" to "47 chartres",
                "phone" to "555-555-5555",
                "photo" to "my photo"
        )

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        val model = UserModel()
        viewModel = ViewModelProviders.of(this, UserModel.Factory(model)).get(UserViewModel::class.java)
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {user->
            Log.d("LEILA THIS IS RESULT", "${user.address} => ${user.name}")
        })
    }
}
