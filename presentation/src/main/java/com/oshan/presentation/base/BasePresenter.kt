package com.oshan.presentation.base

import kotlinx.coroutines.CoroutineScope

interface BasePresenter<V : BaseView> : CoroutineScope {

    fun onAttach(mvpView: V)
    fun onDetach()

}