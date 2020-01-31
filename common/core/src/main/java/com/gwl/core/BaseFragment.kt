package com.gwl.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
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
        mDataBinding.setVariable(getBindingVariable(), mViewModel)
        mDataBinding.lifecycleOwner = this
        mDataBinding.executePendingBindings()
        initObservers()
    }

    // endregion
    fun <T> LiveData<T>.observe(performTask: (it: T) -> Unit) {
        this.observe(this@BaseFragment, Observer {
            performTask(it)
        })
    }


    open fun initObservers() {}
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