package com.blog

import android.view.View
import androidx.lifecycle.LiveData
import com.gwl.MyApplication
import com.gwl.core.BaseViewModel
import com.gwl.core.Common
import com.gwl.core.LoginManager
import com.gwl.model.BlogPostResponse

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