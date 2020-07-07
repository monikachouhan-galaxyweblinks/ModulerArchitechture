package com.blog.addblog

import androidx.lifecycle.MutableLiveData
import com.gwl.core.ActionLiveData
import com.gwl.core.BaseViewModel
import com.gwl.model.PostBlogRequest

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
