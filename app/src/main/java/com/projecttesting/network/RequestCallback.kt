package com.projecttesting.network

interface RequestCallback<T> {

    fun onLoading()

    fun onSuccess(data: T?)

    fun onError(message: String?)

}