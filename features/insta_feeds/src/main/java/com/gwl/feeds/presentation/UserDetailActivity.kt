package com.gwl.feeds.presentation

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.gwl.feeds.BR
import com.gwl.feeds.ExoConfig
import com.gwl.feeds.R
import com.gwl.feeds.adapter.SlidingImageAdapter
import com.gwl.feeds.databinding.ActivityUserDetailBinding
import com.gwl.model.MediaType
import com.gwl.playercore.ToroPlayer
import com.gwl.playercore.media.PlaybackInfo
import com.gwl.playercore.widget.Container
import com.gwl.playerfeed.ExoPlayerViewHelper
import kotlinx.android.synthetic.main.activity_user_detail.*


const val INTENT_KEY_INSTAFEED = "instafeed"

class UserDetailActivity : BaseActivity<ActivityUserDetailBinding, UserDetailViewModel>(),
    ToroPlayer {
    private var helper: ExoPlayerViewHelper? = null
    open var videoUri: Uri? = null
    private var player: SimpleExoPlayer? = null
    var listener: ToroPlayer.EventListener? = null
    private lateinit var videoView: PlayerView
    var fullscreen = false

    override fun getLayoutId(): Int {
        return R.layout.activity_user_detail
    }

    override fun getViewModel(): UserDetailViewModel {
        return initViewModel { UserDetailViewModel() }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFinishAfterTransition()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }

    override fun onPause() {
        super.onPause()
        player?.stop()
    }

    override fun initExtras() {
        super.initExtras()
        mDataBinding.setVariable(BR.viewModel, mViewModel)
        setToolBar(toolbar)
        supportActionBar?.title = "Detail"
        mViewModel.instaFeed = intent?.extras?.getParcelable(INTENT_KEY_INSTAFEED)
        if (mViewModel.instaFeed?.type.equals(
                MediaType.CAROSEL.value, ignoreCase = true
            )
        ) initPager()

        if (mViewModel.instaFeed?.type.equals(MediaType.VIDEO.value, ignoreCase = true)) {
            videoUri = Uri.parse(mViewModel.instaFeed?.videos?.lowResolution?.url)
            createMediaSource()
        }
    }

    private fun createMediaSource() {
        val trackSelector: TrackSelector = DefaultTrackSelector()
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(
                this, Util.getUserAgent(this, "simpleAudioApp")
            )
        val mediaSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .createMediaSource(videoUri)
        playerView.player = player
        player?.prepare(mediaSource)
        initFullScreenButton()

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            fullscreen = false
            changeToPortrait()
        } else if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            fullscreen = true
            changeToLandscape()
        }
        super.onConfigurationChanged(newConfig)
    }

    private fun initFullScreenButton() {
        val fullscreenButton = playerView.findViewById<ImageView>(R.id.exo_fullscreen_icon)
        fullscreenButton.setOnClickListener {
            if (fullscreen) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                changeToPortrait()
                fullscreen = false
            } else {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                changeToLandscape()
                fullscreen = true
            }
        }

    }

    private fun changeToPortrait() {
        val attr = window.attributes
        val window = window
        window.attributes = attr
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        mDataBinding.layoutData.visibility = View.VISIBLE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        if (supportActionBar != null) {
            mDataBinding.appBar.visibility = View.VISIBLE
            supportActionBar?.show()
        }
        //  updateSystemUI(true)
    }

    private fun changeToLandscape() {
        val lp = window.attributes
        val window = window
        window.attributes = lp
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        mDataBinding.layoutData.visibility = View.GONE
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        if (supportActionBar != null) {
            mDataBinding.appBar.visibility = View.GONE
            supportActionBar?.hide()
        }
        updateSystemUI(false)
    }

    private fun updateSystemUI(isShown: Boolean) {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        if (!isShown) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        } else {
            // Shows the system bars by removing all the flags
            // except for the ones that make the content appear under the system bars.
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }
    }

    private fun initPager() {
        val adapter = SlidingImageAdapter()
        adapter.setData(mViewModel.instaFeed?.carosel?.toMutableList())
        pager.adapter = adapter
        indicator.setViewPager(pager)
    }

    override fun getPlayerView() = findViewById<PlayerView>(R.id.playerView)

    override fun getCurrentPlaybackInfo() = helper?.latestPlaybackInfo ?: PlaybackInfo()

    override fun initialize(container: Container, playbackInfo: PlaybackInfo) {
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
        Log.e("VideoFeedViewHolder", " initialize after ${videoUri}")
    }

    override fun play() {
        Log.e("play", " play initialize ${videoUri}")
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
