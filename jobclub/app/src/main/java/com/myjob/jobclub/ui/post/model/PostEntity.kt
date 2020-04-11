package com.myjob.jobclub.ui.post.model

import android.net.Uri
import java.io.Serializable

data class PostEntity(
        var id: String? = null,
        var title: String? = null,
        var description: String? = null,
        var phone: String? = null,
        var createdDate: String? = null,
        var photo: String? = null,
        var photoUri: Uri ? = null,
        var updateDate: String? = null) : Serializable {
}
