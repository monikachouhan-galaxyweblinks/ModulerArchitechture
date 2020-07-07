package com.aucto.core

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * @author GWL
 * @Created on 02/8/19.
 */
abstract class BaseFragment<B : ViewDataBinding, V : BaseViewModel> : Fragment() {

    // region - Public properties
    lateinit var mDataBinding: B
    lateinit var mViewModel: V
    var isFirst: Boolean = false
    val permissionList = listOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    // endregion

    // region - Lifecycle functions
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mDataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // if (isFirst) {
        mDataBinding.setVariable(getBindingVariable(), mViewModel)
        mDataBinding.lifecycleOwner = this
        mDataBinding.executePendingBindings()
        removeObservers()
        initObservers()
        // }
    }

    open fun setToolBar(
        toolbar: Toolbar? = null,
        @DrawableRes icon: Int = R.drawable.ic_toolbar_arrow_back
    ) {
        toolbar?.also {
            (activity as? AppCompatActivity)?.apply {
                setSupportActionBar(it)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(icon)
            }
        }
    }

    // endregion
    fun <T> LiveData<T>.observe(performTask: (it: T) -> Unit) {
        this.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                performTask(it)
            }
        })
    }

    fun requestPermission() {
        // Here, thisActivity is the current activity
        activity?.also {
            if (ContextCompat.checkSelfPermission(it, permissionList[0]) !=
                PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    it, permissionList[1]
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        it,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        it, Manifest.permission.CAMERA
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        it, permissionList.toTypedArray(), Common.PERMISSION_REQ_CODE
                    )
                    // Show an explanation to the user *asynchronously* -- don't block
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(
                        it, permissionList.toTypedArray(), Common.PERMISSION_REQ_CODE
                    )
                }
            }
        }
    }

    fun checkStorageAndCameraPermission(): Boolean {
        activity?.also {
            return ContextCompat.checkSelfPermission(it, permissionList[0]) !=
                    PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                it, permissionList[1]
            ) != PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    open fun initObservers() {}
    open fun removeObservers() {}
    open fun initExtras() {}

    // region - Abstract functions
    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

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

}