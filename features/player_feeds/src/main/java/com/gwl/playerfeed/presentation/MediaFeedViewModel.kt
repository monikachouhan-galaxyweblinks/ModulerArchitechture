package com.gwl.playerfeed.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gwl.MyApplication
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewModel
import com.gwl.core.LoginManager
import com.gwl.model.ArticlesItem
import com.gwl.model.MediaType
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
class MediaFeedViewModel : BaseViewModel(), BaseAdapter.OnItemClickListener<ArticlesItem> {
    override fun onItemClick(item: ArticlesItem) {
        Log.e("clickckck ", "${item.type?.name} fcghfgh")
        when (item.type) {
            MediaType.IMAGE -> imageItemClick.postValue(item)
            MediaType.VIDEO -> videoItemClick.postValue(item)
            MediaType.MP3 -> audioItemClick.postValue(item)
        }
    }

    companion object {
        const val FETCH_SIZE = 5
        const val KEY_AUTO_PLAY_SETTING = "autoplay"
        const val PREFETCH_DISTANCE = 20
    }

    val loginManager: LoginManager by lazy { MyApplication.loginManager }
    val videoItemClick: MutableLiveData<ArticlesItem> = MutableLiveData()
    val imageItemClick: MutableLiveData<ArticlesItem> = MutableLiveData()
    val audioItemClick: MutableLiveData<ArticlesItem> = MutableLiveData()
    val networkAPI: NetworkAPI by lazy { MyApplication.instance.networkAPI }
    val isApiRunning: ObservableField<Boolean> by lazy { ObservableField<Boolean>(true) }
    val mediaFeeds: ObservableField<List<ArticlesItem>> by lazy { ObservableField<List<ArticlesItem>>() }
    val onMediaFeedsFailure: ObservableField<String> by lazy { ObservableField<String>() }
    val pagedListConfig = PagedList.Config.Builder()
        .setInitialLoadSizeHint(FETCH_SIZE)
        .setPageSize(FETCH_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .setEnablePlaceholders(false)
        .build()

    fun initPager(): LiveData<PagedList<ArticlesItem>> {
        isApiRunning.set(true)
        return LivePagedListBuilder<Int, ArticlesItem>(
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
        Log.d("updateAutoPlaySetting", " updateAutoPlaySetting $checked ")
        loginManager.setBoolean(KEY_AUTO_PLAY_SETTING, checked)
    }

    fun getAutoPlayPref(): Boolean {
        return loginManager.getBoolean(KEY_AUTO_PLAY_SETTING) ?: false
    }
}