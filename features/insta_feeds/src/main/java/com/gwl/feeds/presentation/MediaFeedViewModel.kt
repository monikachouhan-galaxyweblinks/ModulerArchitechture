package com.gwl.feeds.presentation

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gwl.MyApplication
import com.gwl.cache.db.AppDatabase
import com.gwl.cache.db.dao.FavoriteDao
import com.gwl.core.BaseViewModel
import com.gwl.core.KEY_ACCESS_TOKEN
import com.gwl.core.KEY_AUTO_PLAY_SETTING
import com.gwl.core.LoginManager
import com.gwl.feeds.MediaDataSourceFactory
import com.gwl.feeds.datasource.MediaFeedDataSource
import com.gwl.model.InstaFeed
import com.gwl.networking.client.server.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// * Created on 28/1/20.
/**
 * @author GWL
 */
class MediaFeedViewModel : BaseViewModel(), AuthenticationListener {

    companion object {
        const val FETCH_SIZE = 5
        const val PREFETCH_DISTANCE = 20
    }

    val loginManager: LoginManager by lazy { MyApplication.loginManager }
    val networkAPI: NetworkAPI by lazy { MyApplication.instance.networkAPI }
    val isApiRunning: ObservableField<Boolean> by lazy { ObservableField<Boolean>(true) }
    val mediaFeeds: ObservableField<List<InstaFeed>> by lazy { ObservableField<List<InstaFeed>>() }
    val showDialog: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val onMediaFeedsFailure: ObservableField<String> by lazy { ObservableField<String>() }
    val favDao: FavoriteDao by lazy { AppDatabase.getInstance(MyApplication.instance).favDao() }
    val dataSource: MediaFeedDataSource by lazy { MediaFeedDataSource() }
    val pagedListConfig = PagedList.Config.Builder()
        .setInitialLoadSizeHint(FETCH_SIZE)
        .setPageSize(FETCH_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .setEnablePlaceholders(false)
        .build()

    var liveData: LiveData<PagedList<InstaFeed>>? = null

    fun initData() {
        if (loginManager.getString(KEY_ACCESS_TOKEN).isNullOrEmpty()) {
            showDialog.postValue(true)
        } else liveData = initPager()
    }

    fun initPager(): LiveData<PagedList<InstaFeed>> {
        isApiRunning.set(true)
        return LivePagedListBuilder<Int, InstaFeed>(
            MediaDataSourceFactory(
                dataSource,
                true
            ), pagedListConfig
        ).build()
    }

    override fun onTokenReceived(auth_token: String?) {
        loginManager.setString(KEY_ACCESS_TOKEN, auth_token ?: "")
        auth_token?.also {
            liveData = initPager()
        }
    }

    fun checkIsFav(id: String): Boolean {
        var isFave = false
        GlobalScope.launch(Dispatchers.IO) {
            val data = favDao.getFavById(id)
            isFave = data != null
        }
        return isFave
    }

    fun updateAutoPlaySetting(checked: Boolean) {
        Log.d("updateAutoPlaySetting", " updateAutoPlaySetting $checked ")
        loginManager.setBoolean(KEY_AUTO_PLAY_SETTING, checked)
    }

    fun getAutoPlayPref(): Boolean {
        return loginManager.getBoolean(KEY_AUTO_PLAY_SETTING) ?: false
    }
}