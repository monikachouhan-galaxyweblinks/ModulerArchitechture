package com.gwl.playerfeed.basic

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.gwl.core.networkdetection.ConnectionLiveData
import com.gwl.core.networkdetection.ConnectionModel
import com.gwl.core.networkdetection.ConnectionType
import com.gwl.model.ArticlesItem
import com.gwl.navigation.features.DetailNavigation
import com.gwl.playerfeed.BR
import com.gwl.playerfeed.R
import com.gwl.playerfeed.databinding.ActivityBasicListBinding
import com.gwl.toro.CacheManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.content_basic_list.*


class BasicListActivity : BaseActivity<ActivityBasicListBinding, BasicListViewModel>() {

    private val adapter = MediaFeedAdapter()
    private val disposable = CompositeDisposable()
    private var connectionMode: ConnectionModel? = null

    companion object {
        const val DATA = "item_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(BR.viewModel, mViewModel)

        container.apply {
            adapter = this@BasicListActivity.adapter
            layoutManager = LinearLayoutManager(this@BasicListActivity)
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
        ConnectionLiveData(this).observe {
            connectionMode = it
            updateAutoPlaySetting()
        }
        mViewModel.onItemClick.observe {
            Log.e("ttttt ","${it.title}")
            showDetail(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_basic_list
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_basic_list, menu)
        val item = menu!!.findItem(R.id.action_autoPlaySettings)
        item.isChecked = mViewModel.getAutoPlayPref()
        return super.onCreateOptionsMenu(menu)
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

    override fun getViewModel(): BasicListViewModel {
        return initViewModel { BasicListViewModel() }
    }

    fun updateAutoPlaySetting() {
        adapter.isAutoPlay = mViewModel.getAutoPlayPref() &&
                connectionMode?.type == ConnectionType.WIFI
        adapter.notifyDataSetChanged()
    }

    private fun showDetail(item: ArticlesItem) = DetailNavigation.dynamicStart?.let {
        it.putExtra(DATA, item)
        it.putExtra("name", "amit")
        startActivity(it)
    }
}
