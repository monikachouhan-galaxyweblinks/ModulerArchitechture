package com.gwl.playerfeed.basic

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gwl.MyApplication
import com.gwl.core.BaseViewModel
import com.gwl.core.LoginManager
import com.gwl.model.MediaFeed
import com.gwl.playerfeed.MediaDataSourceFactory
import com.gwl.playerfeed.datasource.MediaFeedDataSource
import com.networking.client.server.NetworkAPI
import com.networking.result.APIResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// * Created on 28/1/20.
/**
 * @author GWL
 */
class BasicListViewModel : BaseViewModel() {
    companion object {
        const val FETCH_SIZE = 5
        const val KEY_AUTO_PLAY_SETTING = "autoplay"
        const val PREFETCH_DISTANCE = 20
    }

    val loginManager: LoginManager by lazy { MyApplication.loginManager }

    val networkAPI: NetworkAPI by lazy { MyApplication.instance.networkAPI }
    val isApiRunning: ObservableField<Boolean> by lazy { ObservableField<Boolean>(true) }
    val mediaFeeds: ObservableField<List<MediaFeed>> by lazy { ObservableField<List<MediaFeed>>() }
    val onMediaFeedsFailure: ObservableField<String> by lazy { ObservableField<String>() }
    val pagedListConfig = PagedList.Config.Builder()
        .setInitialLoadSizeHint(FETCH_SIZE)
        .setPageSize(FETCH_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .setEnablePlaceholders(false)
        .build()

    fun initPager(): LiveData<PagedList<MediaFeed>> {
        isApiRunning.set(true)
        return LivePagedListBuilder<Int, MediaFeed>(
            MediaDataSourceFactory(
                MediaFeedDataSource(),
                true
            ), pagedListConfig
        ).build()
    }

    fun getList() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = networkAPI.getPlayerFeeds()
            //Log.d("FeedViewModel", "FeedViewModel ${response.execute()}")
            when (response) {
                is APIResult.Success -> {
                    mediaFeeds.set(response.response.data)
                }
                is APIResult.Failure -> {
                    onMediaFeedsFailure.set(response.details.message)
                }
            }
        }

    }

    fun updateAutoPlaySetting(checked: Boolean) {
        Log.d("updateAutoPlaySetting"," updateAutoPlaySetting $checked ")
        loginManager.setBoolean(KEY_AUTO_PLAY_SETTING, checked)
    }

    fun getAutoPlayPref(): Boolean {
        return loginManager.getBoolean(KEY_AUTO_PLAY_SETTING) ?: false
    }
}