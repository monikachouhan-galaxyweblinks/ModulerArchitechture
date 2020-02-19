package com.gwl.feeds.presentation

import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwl.MyApplication
import com.gwl.cache.db.AppDatabase
import com.gwl.cache.db.dao.FavoriteDao
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseFragment
import com.gwl.core.initViewModel
import com.gwl.core.networkdetection.ConnectionLiveData
import com.gwl.core.networkdetection.ConnectionModel
import com.gwl.core.networkdetection.ConnectionType
import com.gwl.feeds.BR
import com.gwl.feeds.MediaListRefreshingLiveData
import com.gwl.feeds.R
import com.gwl.feeds.databinding.ActivityBasicListBinding
import com.gwl.model.ArticlesItem
import com.gwl.model.FavoriteFeed
import com.gwl.model.InstaFeed
import com.gwl.navigation.features.AudioDetailNavigation
import com.gwl.navigation.features.DetailNavigation
import com.gwl.navigation.features.ImageDetailNavigation
import com.gwl.playercore.CacheManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_basic_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InstaFeedFragment : BaseFragment<ActivityBasicListBinding, MediaFeedViewModel>(),
    BaseAdapter.OnItemClickListener<InstaFeed> {

    private val adapter = MediaFeedAdapter()
    private var liveData: LiveData<PagedList<InstaFeed>>? = null
    private val disposable = CompositeDisposable()
    private var connectionMode: ConnectionModel? = null
    val favDao: FavoriteDao by lazy { AppDatabase.getInstance(MyApplication.instance).favDao() }

    companion object {
        const val DATA = "item_data"
    }

    override fun initObservers() {
        super.initObservers()
        mDataBinding.setVariable(BR.viewModel, mViewModel)

        container.apply {
            adapter = this@InstaFeedFragment.adapter
            layoutManager = LinearLayoutManager(context)
            cacheManager = CacheManager.DEFAULT
            showShimmerAdapter()
        }
        container.visibility = View.VISIBLE
        adapter.itemClick = this

        liveData = mViewModel.initPager()
        liveData?.observe {
            adapter.submitList(it)
            mViewModel.isApiRunning.set(false)
        }
        MediaListRefreshingLiveData.observe {
            if (it.isNotEmpty()) container?.hideShimmerAdapter()
        }

        swipeToRefresh?.setOnRefreshListener { refresh() }
        setHasOptionsMenu(true)

        context?.also { it ->
            ConnectionLiveData(it).observe {
                connectionMode = it
                updateAutoPlaySetting()
            }
        }
    }

    override fun onViewClicked(view: View, item: InstaFeed, position: Int) {
        when (view.id) {
            R.id.likeButton -> {
                GlobalScope.launch(Dispatchers.IO) {
                    val islike = favDao.getFavById(item.id)
                    if (islike != null) {
                        mViewModel.favDao.delete(islike)
                    } else {
                        mViewModel.favDao.add(FavoriteFeed(item.id, position, item.likes.count + 1))
                    }
                    withContext(Dispatchers.Main) {
                        adapter.notifyItemChanged(position)
                    }
                }

            }
        }
    }


    override fun onItemClick(item: InstaFeed) {

    }

    private fun refresh() {
        liveData?.value?.dataSource?.invalidate()
        swipeToRefresh?.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_basic_list
    }
/*
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        activity?.menuInflater?.inflate(R.menu.menu_basic_list, menu)
        val item = menu!!.findItem(R.id.action_autoPlaySettings)
        item.isChecked = mViewModel.getAutoPlayPref()
        super.onCreateOptionsMenu(menu, inflater)
    }
*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_autoPlaySettings -> {
                item.isChecked = !item.isChecked
                mViewModel.updateAutoPlaySetting(item.isChecked)
                updateAutoPlaySetting()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getViewModel(): MediaFeedViewModel {
        return initViewModel { MediaFeedViewModel() }
    }

    fun updateAutoPlaySetting() {
        adapter.isAutoPlay = mViewModel.getAutoPlayPref() &&
                connectionMode?.type == ConnectionType.WIFI
        adapter.notifyDataSetChanged()
    }

    private fun showVideoDetail(item: ArticlesItem) = DetailNavigation.dynamicStart?.let {
        it.putExtra(DATA, item)
        startActivity(it)
    }

    private fun showImageDetail(item: ArticlesItem) = ImageDetailNavigation.dynamicStart?.let {
        it.putExtra(DATA, item)
        startActivity(it)
    }

    private fun showAudioDetail(item: ArticlesItem) = AudioDetailNavigation.dynamicStart.let {
        it?.putExtra(DATA, item)
        startActivity(it)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

}
