package com.gwl.detail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.gwl.model.ArticlesItem
import com.gwl.playerfeed.BR
import com.gwl.playerfeed.ExoPlayerViewHelper
import com.gwl.playerfeed.R
import com.gwl.playerfeed.basic.BasicListActivity
import com.gwl.playerfeed.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>()/*,ToroPlayer*/ {
    /* override fun getPlayerView(): View {
         return playerView
     }

     override fun getCurrentPlaybackInfo(): PlaybackInfo {
       return  helper?.latestPlaybackInfo ?: PlaybackInfo()
     }

     override fun initialize(container: Container, playbackInfo: PlaybackInfo) {
     }

     override fun play() {
     }

     override fun pause() {
     }

     override fun isPlaying(): Boolean {
     }

     override fun release() {
     }

     override fun wantsToPlay(): Boolean {
     }

     override fun getPlayerOrder(): Int {
     }*/

    lateinit var item: ArticlesItem
    private var helper: ExoPlayerViewHelper? = null
    private var videoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        item = intent.getParcelableExtra<ArticlesItem>(
            BasicListActivity.DATA
        )
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(BR.viewModel, mViewModel)
        mDataBinding.setVariable(BR.item, item)
        Log.e(
            "dadad ", "${intent.getParcelableExtra<ArticlesItem>(
                BasicListActivity.DATA
            ).title}  ${intent.getStringExtra("name")}"
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun getViewModel(): DetailViewModel {
        return initViewModel {
            DetailViewModel(item)
        }
    }

    fun initlizePlayer() {
        /*   if (helper == null) helper = ExoPlayerViewHelper(
               this,
               videoUri!!,
               null,
               MyApplication.config!!
           )*/
    }
}
