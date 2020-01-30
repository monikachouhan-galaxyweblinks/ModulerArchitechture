package com.gwl.playerfeed.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwl.core.BaseFragment
import com.gwl.core.initViewModel
import com.gwl.core.networkdetection.ConnectionLiveData
import com.gwl.core.networkdetection.ConnectionModel
import com.gwl.core.networkdetection.ConnectionType
import com.gwl.model.ArticlesItem
import com.gwl.navigation.features.AudioDetailNavigation
import com.gwl.navigation.features.DetailNavigation
import com.gwl.navigation.features.ImageDetailNavigation
import com.gwl.playerfeed.BR
import com.gwl.playerfeed.R
import com.gwl.playerfeed.databinding.ActivityBasicListBinding
import com.gwl.toro.CacheManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.content_basic_list.*


class MediaFeedFragment : BaseFragment<ActivityBasicListBinding, MediaFeedViewModel>() {

    private val adapter = MediaFeedAdapter()
    private val disposable = CompositeDisposable()
    private var connectionMode: ConnectionModel? = null

    companion object {
        const val DATA = "item_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mDataBinding.setVariable(BR.viewModel, mViewModel)
        container.apply {
            adapter = this@MediaFeedFragment.adapter
            layoutManager = LinearLayoutManager(context)
            cacheManager = CacheManager.DEFAULT
        }
        adapter.itemClick = mViewModel
        mViewModel.initPager().observe {
            adapter.submitList(it)
            mViewModel.isApiRunning.set(false)
        }
    }

    override fun initObservers() {
        super.initObservers()
        context?.also { it ->
            ConnectionLiveData(it).observe {
                connectionMode = it
                updateAutoPlaySetting()
            }
        }
        mViewModel.audioItemClick.observe { showAudioDetail(it) }
        mViewModel.videoItemClick.observe { showVideoDetail(it) }
        mViewModel.imageItemClick.observe { showImageDetail(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_basic_list
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        activity?.menuInflater?.inflate(R.menu.menu_basic_list, menu)
        val item = menu!!.findItem(R.id.action_autoPlaySettings)
        item.isChecked = mViewModel.getAutoPlayPref()
        super.onCreateOptionsMenu(menu, inflater)
    }

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
