package com.myjob.jobclub.ui.user.model

data class Users(
    val id: String,
    val users: List<User>

) {
}

data class User(
    val name: String,
    val userName: String,
    val address: String,
    val phone: String,
    val photo: String
) {

}