package com.aucto.addblog

import androidx.lifecycle.MutableLiveData
import com.aucto.core.ActionLiveData
import com.aucto.core.BaseViewModel
import com.aucto.model.PostBlogRequest

class AddBlogViewModel(private val repository: AddBlogRepository) : BaseViewModel() {

    var onSubmitClick = ActionLiveData<Boolean>()
    var onBlogPosted = MutableLiveData<Boolean>()


    fun onSubmitClick() {
        onSubmitClick.sendAction(true)
    }

    suspend fun postBlog(blogRequest: PostBlogRequest) {
        repository.addBlog(blogRequest, onBlogPosted)
    }
}
