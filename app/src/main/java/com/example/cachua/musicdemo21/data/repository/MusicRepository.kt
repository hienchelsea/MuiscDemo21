package com.example.cachua.musicdemo21.data.repository


import com.example.cachua.musicdemo21.data.model.MusicModel
import com.example.cachua.musicdemo21.data.model.OnDataLoadedCallback
import com.example.cachua.musicdemo21.data.source.MusicDatasource
import java.net.MalformedURLException

class MusicRepository(private val localDataSource: MusicDatasource.Local) : MusicDatasource.Local {

    override fun getMusics(onDataLoadedCallback: OnDataLoadedCallback<ArrayList<MusicModel>>) {
        try {
            localDataSource.getMusics(onDataLoadedCallback)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            onDataLoadedCallback.onDataNotAvailable(e)
        }
    }
}
