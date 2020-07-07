package com.aucto.addblog

import android.os.Bundle
import android.widget.Toast
import com.blog.R
import com.blog.databinding.ActivityAddBlogBinding
import com.aucto.MyApplication
import com.aucto.core.BaseActivity
import com.aucto.core.initViewModel
import com.aucto.model.PostBlogRequest
import kotlinx.android.synthetic.main.activity_add_blog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddBlogActivity : BaseActivity<ActivityAddBlogBinding, AddBlogViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_blog)
        mDataBinding.viewModel = getViewModel()
        setToolBar(toolbar)
        supportActionBar?.title = getString(R.string.add_blog)
        initObserver()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_add_blog
    }

    override fun getViewModel(): AddBlogViewModel {
        return initViewModel { AddBlogViewModel(AddBlogRepository(MyApplication.instance.networkAPI)) }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFinishAfterTransition()
        return super.onSupportNavigateUp()
    }

    private fun initObserver() {
        mViewModel.onBlogPosted.observe {
            if (it)
                Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
            else Toast.makeText(this, getString(R.string.failed), Toast.LENGTH_SHORT).show()
            finish()
        }
        mDataBinding.btnSubmit.setOnClickListener {
            validatePost()
        }
    }

    private fun validatePost() {
        when {
            mDataBinding.etTitle.toString().trim().isEmpty() -> {
                Toast.makeText(
                    this@AddBlogActivity,
                    getString(R.string.all_fields_are_mandatory),
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            mDataBinding.etBody.toString()
                .trim().isEmpty() -> {
                Toast.makeText(
                    this@AddBlogActivity,
                    getString(R.string.all_fields_are_mandatory),
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            else -> {
                GlobalScope.launch(Dispatchers.IO) {
                mViewModel.postBlog(
                    PostBlogRequest(
                        mDataBinding.etTitle.text.toString(),
                        mDataBinding.etBody.text.toString(), 1
                    )
                )
            }

            finish()
        }
        }
    }

}