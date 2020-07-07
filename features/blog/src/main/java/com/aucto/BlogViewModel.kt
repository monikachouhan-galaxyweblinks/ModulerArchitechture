package com.aucto

import android.view.View
import androidx.lifecycle.LiveData
import com.aucto.core.BaseViewModel
import com.aucto.core.Common
import com.aucto.core.LoginManager
import com.aucto.model.BlogPostResponse

/**
 * @author GWL
 */
class BlogViewModel : BaseViewModel() {
    val loginManager: LoginManager by lazy { MyApplication.loginManager }
    private val blogDao by lazy { MyApplication.database.blogDao() }
    fun getBlogData(): LiveData<List<BlogPostResponse>> {
        return blogDao.getAllBlogList()
    }

    fun onFocusChanged(view: View, hasFocus: Boolean) {
        Common.showKeyboard(view, hasFocus)

    }
}