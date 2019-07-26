package com.example.cachua.musicdemo21.data.source

import android.content.Context
import android.provider.MediaStore
import com.example.cachua.musicdemo21.data.model.MusicModel

class ContentResolverData(private val context: Context) {

    fun getData(): ArrayList<MusicModel> {
        val musics: ArrayList<MusicModel> = ArrayList()
        val projection = arrayOf(MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID
        )
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, "LOWER(" + MediaStore.Audio.Media.TITLE
                + ") ASC")

        while (cursor.moveToNext()) {
            musics.add(MusicModel(cursor))
        }
        cursor.close()

        return musics
    }
}
