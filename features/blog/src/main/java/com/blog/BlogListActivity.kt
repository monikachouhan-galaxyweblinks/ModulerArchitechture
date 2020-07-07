package com.blog

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.blog.addblog.AddBlogActivity
import com.blog.databinding.ActivityBlogListBinding
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import kotlinx.android.synthetic.main.activity_blog_list.*


class BlogListActivity : BaseActivity<ActivityBlogListBinding, BlogViewModel>() {

    private val listAdapter by lazy { BlogAdapter() }
    override fun getLayoutId(): Int {
        return R.layout.activity_blog_list
    }

    override fun getViewModel(): BlogViewModel {
        return initViewModel { BlogViewModel() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.btnAddBlog.setOnClickListener {
            startActivity(
                Intent(
                    this@BlogListActivity,
                    AddBlogActivity::class.java
                )
            )
        }
        initView()
    }
    override fun onSupportNavigateUp(): Boolean {
        supportFinishAfterTransition()
        return super.onSupportNavigateUp()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_add -> startActivity(
                Intent(
                    this@BlogListActivity,
                    AddBlogActivity::class.java
                )
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        recyclerViewData.adapter = listAdapter
        mViewModel.getBlogData().observe(this@BlogListActivity, Observer {
            if (it.isNotEmpty()) listAdapter.blogPostResponseList = it.reversed()
        })
    }
}
