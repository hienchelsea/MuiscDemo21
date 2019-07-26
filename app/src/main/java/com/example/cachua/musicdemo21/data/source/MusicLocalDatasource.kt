package com.example.cachua.musicdemo21.data.source

import com.example.cachua.musicdemo21.data.model.MusicModel
import com.example.cachua.musicdemo21.data.model.OnDataLoadedCallback


class MusicLocalDatasource(private val contentResolverData: ContentResolverData) : MusicDatasource.Local {

    override fun getMusics(onDataLoadedCallback: OnDataLoadedCallback<ArrayList<MusicModel>>) {
        ReadExternalMusicTask(contentResolverData, onDataLoadedCallback).execute()
    }
}
