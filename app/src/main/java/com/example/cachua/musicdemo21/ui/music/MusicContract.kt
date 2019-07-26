package com.example.cachua.musicdemo21.ui.music

import com.example.cachua.musicdemo21.data.model.MusicModel


interface MusicContract {
    interface View{
        fun displayListMusic(musicList:ArrayList<MusicModel>)
        fun onError()
    }
    interface Presenter{
        fun loadDisPlayListMusic()
    }
}
