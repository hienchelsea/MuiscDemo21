package com.example.cachua.musicdemo21.ui.music


import com.example.cachua.musicdemo21.data.model.MusicModel
import com.example.cachua.musicdemo21.data.model.OnDataLoadedCallback
import kotlin.collections.ArrayList
import com.example.cachua.musicdemo21.data.repository.MusicRepository


class MusicPresenter(private val musicRepository: MusicRepository, private val musicActivity: MusicContract.View) : MusicContract.Presenter {
    override fun loadDisPlayListMusic() {
        musicRepository.getMusics(object : OnDataLoadedCallback<ArrayList<MusicModel>> {
            override fun onDataLoaded(data: ArrayList<MusicModel>) {
                musicActivity.displayListMusic(data)
            }

            override fun onDataNotAvailable(exception: Exception) {
                musicActivity.onError()
            }
        })
    }
}
