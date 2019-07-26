package com.example.cachua.musicdemo21.data.source

import android.os.AsyncTask
import com.example.cachua.musicdemo21.data.model.MusicModel
import com.example.cachua.musicdemo21.data.model.OnDataLoadedCallback

class ReadExternalMusicTask(private val contentResolverData: ContentResolverData, private val callback: OnDataLoadedCallback<ArrayList<MusicModel>>) : AsyncTask<Void, Void, ArrayList<MusicModel>>() {

    override fun doInBackground(vararg params: Void?): ArrayList<MusicModel> {
        return contentResolverData.getData()
    }

    override fun onPostExecute(result: ArrayList<MusicModel>?) {
        if (result == null) {
            callback.onDataNotAvailable(NullPointerException())
        } else {
            callback.onDataLoaded(result)
        }
    }
}
