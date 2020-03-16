package com.gwl.feeds.presentation

import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.gwl.feeds.BR
import com.gwl.feeds.R
import com.gwl.feeds.adapter.SlidingImageAdapter
import com.gwl.feeds.databinding.ActivityUserDetailBinding
import com.gwl.model.MediaType
import kotlinx.android.synthetic.main.activity_user_detail.*

const val INTENT_KEY_INSTAFEED = "instafeed"

class UserDetailActivity : BaseActivity<ActivityUserDetailBinding, UserDetailViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_user_detail
    }

    override fun getViewModel(): UserDetailViewModel {
        return initViewModel { UserDetailViewModel() }
    }

    override fun initExtras() {
        super.initExtras()
        mDataBinding.setVariable(BR.viewModel, mViewModel)
        setToolBar(toolbar)
        supportActionBar?.title = "Detail"
       mViewModel.instaFeed = intent?.extras?.getParcelable(INTENT_KEY_INSTAFEED)
      //  mViewModel.instaFeed = intent?.getParcelableExtra(INTENT_KEY_INSTAFEED)
        if (mViewModel.instaFeed?.type.equals(
                MediaType.CAROSEL.value,
                ignoreCase = true
            )
        ) initPager()
    }

    fun initPager() {
        val adapter = SlidingImageAdapter()
        adapter.setData(mViewModel.instaFeed?.carosel?.toMutableList())
        pager.adapter = adapter
        indicator.setViewPager(pager)
    }
}
