package com.gwl.details

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.gwl.model.ArticlesItem
import com.gwl.playerfeed.BR
import com.gwl.playerfeed.R
import com.gwl.playerfeed.basic.BasicListActivity
import com.gwl.playerfeed.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*


class VideoDetailActivity : BaseActivity<ActivityDetailBinding, VideoDetailViewModel>() {
    lateinit var item: ArticlesItem
    var fullscreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        getIntentData()
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(BR.viewModel, mViewModel)
        mDataBinding.setVariable(BR.item, item)
        initPlayerView()
        setupToolbar(item.title, true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


    private fun getIntentData() {
        item = intent.getParcelableExtra(BasicListActivity.DATA) as ArticlesItem
    }

    private fun initPlayerView() {
        val PlayerView1 = playerViewController as PlayerView
        val player = ExoPlayerFactory.newSimpleInstance(this)
        PlayerView1.player = player
        val uri = Uri.parse(item.sourceUrl)
        val mediaSource = buildMediaSource(uri)
        player.playWhenReady = true
        player.prepare(mediaSource, false, false)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, "exoplayer-codelab")
        val mediaSourceFactory = ProgressiveMediaSource.Factory(dataSourceFactory)
        val mediaSource1 = mediaSourceFactory.createMediaSource(uri)
        return ConcatenatingMediaSource(mediaSource1)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun getViewModel(): VideoDetailViewModel {
        return initViewModel {
            VideoDetailViewModel(item)
        }
    }

    fun fullScreen() {
       /* val fullscreenButton =
            playerViewController.findViewById(R.id.exo_fullscreen_icon) as ImageView
        fullscreenButton.setOnClickListener {
            if (fullscreen) {
                fullscreenButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@VideoDetailActivity,
                        R.drawable.exo_controls_fullscreen_enter
                    )
                )
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                if (supportActionBar != null) {
                    supportActionBar!!.show()
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                val params = playerViewController.layoutParams as LinearLayout.LayoutParams
                params.width = params.MATCH_PARENT
                params.height =
                    (200 * applicationContext.resources.displayMetrics.density).toInt()
                playerViewController.layoutParams = params
                fullscreen = false
            } else {
                fullscreenButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@VideoDetailActivity,
                        R.drawable.exo_controls_fullscreen_exit
                    )
                )
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                if (supportActionBar != null) {
                    supportActionBar!!.hide()
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                val params = playerViewController.layoutParams as RelativeLayout.LayoutParams
                params.width = params.MATCH_PARENT
                params.height = params.MATCH_PARENT
                playerViewController.layoutParams = params
                fullscreen = true
            }
        }*/

    }
}
