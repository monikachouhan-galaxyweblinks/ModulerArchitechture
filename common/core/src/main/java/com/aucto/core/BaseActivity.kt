package com.aucto.core

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.aucto.core.Common.Companion.PERMISSION_REQ_CODE

/**
 * @author GWL
 * @Created on 02/8/19.
 */
abstract class BaseActivity<B : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    // region - Public properties
    lateinit var mDataBinding: B
    lateinit var mViewModel: V
    val permissionList =
        listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    // endregion

    // region - Lifecycle functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }
    // endregion

    // region - Abstract functions
    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V
    // endregion

    // region - Private functions
    private fun performDataBinding() {
        mDataBinding = DataBindingUtil.setContentView<B>(this, getLayoutId())
        this.mViewModel = getViewModel()
        //TODO - Need to set binding variables
        // mDataBinding.setVariable(BR.viewModel, mViewModel)
        mDataBinding.executePendingBindings()
        initObservers()
        initExtras()
        setToolBar()
    }
    // endregion

    // region - Public functions
    fun <T> LiveData<T>.observe(performTask: (it: T) -> Unit) {
        this.observe(this@BaseActivity, Observer {
            performTask(it)
        })
    }


    open fun initObservers() {}

    open fun initExtras() {}

    open fun setToolBar(
        toolbar: Toolbar? = null,
        @DrawableRes icon: Int = R.drawable.ic_toolbar_arrow_back
    ) {
        toolbar?.also {
            setSupportActionBar(it)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(icon)
        }

    }

    fun requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, permissionList[0]) !=
            PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this, permissionList[1]
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.CAMERA
                )
            ) {
                ActivityCompat.requestPermissions(
                    this, permissionList.toTypedArray(), PERMISSION_REQ_CODE
                )
                // Show an explanation to the user *asynchronously* -- don't block
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, permissionList.toTypedArray(), PERMISSION_REQ_CODE
                )
            }
        }
    }

    fun checkStorageAndCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, permissionList[0]) !=
                PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            this, permissionList[1]
        ) != PackageManager.PERMISSION_GRANTED
    }
    // endregion

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun setupToolbar(title: String?, showBackButton: Boolean) {
        supportActionBar?.setHomeButtonEnabled(showBackButton)
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
        supportActionBar?.title = title
    }

}