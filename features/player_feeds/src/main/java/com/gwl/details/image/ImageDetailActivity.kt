package com.gwl.details

import android.os.Bundle
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.gwl.details.image.ImageDetailViewModel
import com.gwl.model.ArticlesItem
import com.gwl.playerfeed.BR
import com.gwl.playerfeed.R
import com.gwl.playerfeed.databinding.ActivityImageDetailBinding
import com.gwl.playerfeed.presentation.MediaFeedActivity


class ImageDetailActivity : BaseActivity<ActivityImageDetailBinding, ImageDetailViewModel>() {
    lateinit var item: ArticlesItem

    override fun onCreate(savedInstanceState: Bundle?) {
        getIntentData()
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(BR.viewModel, mViewModel)
        mDataBinding.setVariable(BR.item, item)
        setupToolbar(item.title, true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getIntentData() {
        item = intent.getParcelableExtra(MediaFeedActivity.DATA) as ArticlesItem
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_image_detail
    }

    override fun getViewModel(): ImageDetailViewModel {
        return initViewModel {
            ImageDetailViewModel(item)
        }
    }
}
