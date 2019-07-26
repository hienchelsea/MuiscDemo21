package com.example.cachua.musicdemo21.data.model

interface OnDataLoadedCallback<T> {

    fun onDataLoaded(data: T)

    fun onDataNotAvailable(exception: Exception)
}
