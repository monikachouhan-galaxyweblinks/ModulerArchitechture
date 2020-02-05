package com.gwl.feeds.presentation

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gwl.MyApplication
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewModel
import com.gwl.core.LoginManager
import com.gwl.feeds.MediaDataSourceFactory
import com.gwl.feeds.R
import com.gwl.feeds.datasource.MediaFeedDataSource
import com.gwl.model.InstaFeed
import com.gwl.model.MediaType
import com.networking.client.server.NetworkAPI

// * Created on 28/1/20.
/**
 * @author GWL
 */
class MediaFeedViewModel : BaseViewModel(), BaseAdapter.OnItemClickListener<InstaFeed> {
    override fun onItemClick(item: InstaFeed) {
        when (item.type) {
            MediaType.IMAGE.value -> imageItemClick.postValue(item)
            MediaType.VIDEO.value -> videoItemClick.postValue(item)
            MediaType.MP3.value -> audioItemClick.postValue(item)
        }
    }

    override fun onViewClicked(view: View, item: InstaFeed) {
        when (view.id) {
            R.id.likeButton -> {

            }
        }
    }

    companion object {
        const val FETCH_SIZE = 5
        const val KEY_AUTO_PLAY_SETTING = "autoplay"
        const val PREFETCH_DISTANCE = 20
    }

    val loginManager: LoginManager by lazy { MyApplication.loginManager }
    val videoItemClick: MutableLiveData<InstaFeed> = MutableLiveData()
    val imageItemClick: MutableLiveData<InstaFeed> = MutableLiveData()
    val audioItemClick: MutableLiveData<InstaFeed> = MutableLiveData()
    val networkAPI: NetworkAPI by lazy { MyApplication.instance.networkAPI }
    val isApiRunning: ObservableField<Boolean> by lazy { ObservableField<Boolean>(true) }
    val mediaFeeds: ObservableField<List<InstaFeed>> by lazy { ObservableField<List<InstaFeed>>() }
    val onMediaFeedsFailure: ObservableField<String> by lazy { ObservableField<String>() }
    val pagedListConfig = PagedList.Config.Builder()
        .setInitialLoadSizeHint(FETCH_SIZE)
        .setPageSize(FETCH_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .setEnablePlaceholders(false)
        .build()

    fun initPager(): LiveData<PagedList<InstaFeed>> {
        isApiRunning.set(true)
        return LivePagedListBuilder<Int, InstaFeed>(
            MediaDataSourceFactory(
                MediaFeedDataSource(),
                true
            ), pagedListConfig
        ).build()
    }

    /*fun getList() {
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

    }*/

    fun updateAutoPlaySetting(checked: Boolean) {
        Log.d("updateAutoPlaySetting", " updateAutoPlaySetting $checked ")
        loginManager.setBoolean(KEY_AUTO_PLAY_SETTING, checked)
    }

    fun getAutoPlayPref(): Boolean {
        return loginManager.getBoolean(KEY_AUTO_PLAY_SETTING) ?: false
    }
}