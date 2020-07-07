package com.aucto.core

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

/**
 * @author GWL
 *
 */
abstract class BaseViewModel : ViewModel() {

    open var toolBarTitle: ObservableField<String> = ObservableField()
    open var toolBarColor: ObservableField<Int> = ObservableField()
//    open var toolBarTitle: String = ""
//    open var toolBarColor: Int = R.color.white
}

