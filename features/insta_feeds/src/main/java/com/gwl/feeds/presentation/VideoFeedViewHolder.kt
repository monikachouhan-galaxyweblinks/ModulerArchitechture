/*
 * Copyright (c) 2018 Nam Nguyen, nam@gwl.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gwl.feeds.presentation

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.gwl.MyApplication
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewHolder
import com.gwl.feeds.BR
import com.gwl.feeds.ExoConfig
import com.gwl.feeds.R
import com.gwl.model.InstaFeed
import com.gwl.playercore.ToroPlayer
import com.gwl.playercore.ToroPlayer.EventListener
import com.gwl.playercore.ToroUtil.visibleAreaOffset
import com.gwl.playercore.media.PlaybackInfo
import com.gwl.playercore.widget.Container
import com.gwl.playerfeed.ExoPlayerViewHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author eneim (2018/01/23).
 */
open class VideoFeedViewHolder(itemRowBind: ViewDataBinding) :
    BaseViewHolder<InstaFeed>(itemRowBind), ToroPlayer {

    companion object {
        const val defaultRatio = 100 * 165.78F / 360F // magic number.
    }

    private val favDao by lazy { MyApplication.database.favDao() }

    private val playerFrame by lazy { itemView as AspectRatioFrameLayout }
    val player = itemView.findViewById(R.id.playerView) as PlayerView
    private var helper: ExoPlayerViewHelper? = null
    open var videoUri: Uri? = null
    var listener: EventListener? = null
    open var autoplay: Boolean = false
    val likeCount: ObservableInt by lazy { ObservableInt(0) }
    val isPlaying: ObservableBoolean by lazy { ObservableBoolean(false) }

    override fun bind(
        data: InstaFeed,
        onItemClickListener: BaseAdapter.OnItemClickListener<InstaFeed>?
    ) {
        super.bind(data, onItemClickListener)

        val count = if (checkIsFav(data.id)) data.likes.count + 1 else data.likes.count
        data.likes.count = count
        itemRowBinding.setVariable(BR.item, data)
        itemRowBinding.setVariable(BR.isPlaying, isPlaying)
        itemRowBinding.setVariable(BR.position, adapterPosition)
        itemRowBinding.setVariable(BR.isFave, isFavorite)
        itemRowBinding.setVariable(BR.likeCount, likeCount)

        videoUri = Uri.parse(data.videos?.lowResolution?.url)
        itemRowBinding.setVariable(BR.itemClick, onItemClickListener)
        //playerFrame.setAspectRatio(16/9f)
        Log.d("VideoFeedViewHolder", "VideoFeedViewHolder count $count ")
    }

    fun checkIsFav(id: String): Boolean {
        var isFave = false
        GlobalScope.launch(Dispatchers.IO) {
            val data = favDao.getFavById(id)
            isFave = data != null && adapterPosition == data.position
            isFavorite.set(isFave)
        }

        return isFave
    }

    override fun getPlayerView() = player

    override fun getCurrentPlaybackInfo() = helper?.latestPlaybackInfo ?: PlaybackInfo()

    override fun initialize(container: Container, playbackInfo: PlaybackInfo) {
        if (helper == null) helper = ExoPlayerViewHelper(
            this,
            videoUri!!,
            null,
            ExoConfig.config!!
        )
        if (listener == null) {
            listener = object : EventListener {
                override fun onFirstFrameRendered() {
                    Log.d("VideoFeedViewHolder", " initialize onFirstFrameRendered")
                }

                override fun onBuffering() {
                    isPlaying.set(false)
                }

                override fun onPlaying() {
                    isPlaying.set(true)
                }

                override fun onPaused() {
                    isPlaying.set(false)
                }

                override fun onCompleted() {
                    isPlaying.set(false)
                }
            }
            helper!!.addPlayerEventListener(listener!!)
        }
        helper!!.initialize(container, playbackInfo)
    }

    override fun play() {
        helper!!.play(autoplay)
    }

    override fun pause() {
        helper!!.pause()
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

    override fun wantsToPlay() = visibleAreaOffset(this, itemView.parent) >= 0.65

    override fun getPlayerOrder() = adapterPosition

}