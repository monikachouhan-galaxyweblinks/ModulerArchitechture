package com.gwl.details.audio

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.gwl.details.VideoDetailViewModel
import com.gwl.model.ArticlesItem
import com.gwl.playerfeed.BR
import com.gwl.playerfeed.R
import com.gwl.playerfeed.databinding.ActivityAudioDetailBinding
import com.gwl.playerfeed.presentation.MediaFeedFragment
import kotlinx.android.synthetic.main.activity_audio_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AudioDetailActivity : BaseActivity<ActivityAudioDetailBinding, VideoDetailViewModel>() {
    lateinit var item: ArticlesItem

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
        item = intent.getParcelableExtra(MediaFeedFragment.DATA) as ArticlesItem
    }

    private fun initPlayerView() {
        val player = ExoPlayerFactory.newSimpleInstance(this)
        val PlayerView1 = playerView as PlayerView
        PlayerView1.player = player
        val uri = Uri.parse(item.sourceUrl)
        val mediaSource = buildMediaSource(uri)
        player.playWhenReady = true
        player.prepare(mediaSource, false, false)
        PlayerView1.findViewById<ImageView>(R.id.exo_artwork)
            .setImageResource(R.drawable.music_bg_thumbnail)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, "exoplayer-codelab")
        val mediaSourceFactory = ProgressiveMediaSource.Factory(dataSourceFactory)
        val mediaSource1 = mediaSourceFactory.createMediaSource(uri)
        return ConcatenatingMediaSource(mediaSource1)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_audio_detail
    }

    override fun getViewModel(): VideoDetailViewModel {
        return initViewModel {
            VideoDetailViewModel(item)
        }
    }
}
