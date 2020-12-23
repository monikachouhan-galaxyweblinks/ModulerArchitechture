package com.gwl.playerfeed.presentation

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.gwl.MyApplication
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewModel
import com.gwl.core.LoginManager
import com.gwl.model.ArticlesItem
import com.gwl.model.MediaType
import com.gwl.networking.client.server.NetworkAPI
import com.gwl.networking.result.APIResult
import com.gwl.playerfeed.MediaDataSourceFactory
import com.gwl.playerfeed.datasource.MediaFeedDataSource
import com.gwl.playerfeed.datasource.PostDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

// * Created on 28/1/20.
/**
 * @author GWL
 */
@ExperimentalCoroutinesApi
class MediaFeedViewModel : BaseViewModel(), BaseAdapter.OnItemClickListener<ArticlesItem> {
    override fun onItemClick(item: ArticlesItem, view: View) {
        Log.e("clickckck ", "${item.type?.name} fcghfgh")
        when (item.type) {
            MediaType.IMAGE -> imageItemClick.value = item
            MediaType.VIDEO -> videoItemClick.value = item
            MediaType.MP3 -> audioItemClick.value = item
        }
    }

    override fun onViewClicked(view: View, item: ArticlesItem, position: Int) {
        // TODO("Not yet implemented")
    }

    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object {
        const val FETCH_SIZE = 5
        const val KEY_AUTO_PLAY_SETTING = "autoplay"
        const val PREFETCH_DISTANCE = 20
    }

    val loginManager: LoginManager by lazy { MyApplication.loginManager }
    val videoItemClick: MutableStateFlow<ArticlesItem> = MutableStateFlow(ArticlesItem())
    val imageItemClick: MutableStateFlow<ArticlesItem> = MutableStateFlow(ArticlesItem())
    val audioItemClick: MutableStateFlow<ArticlesItem> = MutableStateFlow(ArticlesItem())
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

    fun initPaged3Data() = Pager(PagingConfig(pageSize = 6)) {
        PostDataSource(networkAPI)
    }.flow.cachedIn(viewModelScope)

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