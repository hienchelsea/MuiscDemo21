package com.example.cachua.musicdemo21.data.source

import com.example.cachua.musicdemo21.data.model.MusicModel
import com.example.cachua.musicdemo21.data.model.OnDataLoadedCallback

interface MusicDatasource {
    interface Local {
        fun getMusics(onDataLoadedCallback: OnDataLoadedCallback<ArrayList<MusicModel>>)
    }
}
