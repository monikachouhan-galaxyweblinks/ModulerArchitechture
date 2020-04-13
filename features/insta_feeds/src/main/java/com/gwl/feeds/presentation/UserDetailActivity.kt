package com.gwl.feeds.presentation

import android.net.Uri
import android.util.Log
import android.view.View
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.play.core.internal.r
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.gwl.feeds.BR
import com.gwl.feeds.ExoConfig
import com.gwl.feeds.R
import com.gwl.feeds.adapter.SlidingImageAdapter
import com.gwl.feeds.databinding.ActivityUserDetailBinding
import com.gwl.model.MediaType
import com.gwl.playercore.ToroPlayer
import com.gwl.playercore.ToroUtil
import com.gwl.playercore.media.PlaybackInfo
import com.gwl.playercore.widget.Container
import com.gwl.playerfeed.ExoPlayerViewHelper
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.exo_article_part_video.*

const val INTENT_KEY_INSTAFEED = "instafeed"

class UserDetailActivity : BaseActivity<ActivityUserDetailBinding, UserDetailViewModel>(),
    ToroPlayer {
    private var helper: ExoPlayerViewHelper? = null
    open var videoUri: Uri? = null
    var listener: ToroPlayer.EventListener? = null
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

        if (mViewModel.instaFeed?.type.equals(MediaType.VIDEO.value, ignoreCase = true))
        videoUri = Uri.parse(mViewModel.instaFeed?.videos?.lowResolution?.url)

    }

    fun initPager() {
        val adapter = SlidingImageAdapter()
        adapter.setData(mViewModel.instaFeed?.carosel?.toMutableList())
        pager.adapter = adapter
        indicator.setViewPager(pager)
    }

    override fun getPlayerView() = findViewById<PlayerView>(R.id.playerView)

    override fun getCurrentPlaybackInfo() = helper?.latestPlaybackInfo ?: PlaybackInfo()

    override fun initialize(container: Container, playbackInfo: PlaybackInfo) {
        Log.d("VideoFeedViewHolder", " initialize initialize")
        videoUri?.also {
            if (helper == null) helper = ExoPlayerViewHelper(
                this,
                videoUri!!,
                null,
                ExoConfig.config!!
            )
            if (listener == null) {
                listener = object : ToroPlayer.EventListener {
                    override fun onFirstFrameRendered() {
                        Log.d("VideoFeedViewHolder", " initialize onFirstFrameRendered")
                    }

                    override fun onBuffering() {
                        // isPlaying.set(false)
                    }

                    override fun onPlaying() {
                        //isPlaying.set(true)
                    }

                    override fun onPaused() {
                        //isPlaying.set(false)
                    }

                    override fun onCompleted() {
                        // isPlaying.set(false)
                    }
                }
                helper!!.addPlayerEventListener(listener!!)
            }
            helper!!.initialize(container, playbackInfo)
        }
    }

    override fun play() {
        helper?.play(true)
    }

    override fun pause() {
        helper?.pause()
    }

    override fun isPlaying() = helper?.isPlaying ?: false

    override fun release() {
        if (listener != null) {
            helper?.removePlayerEventListener(listener)
            listener = null
        }
        helper?.release()
        helper = null
    }

    override fun wantsToPlay() = true
    override fun getPlayerOrder(): Int = 1
}
