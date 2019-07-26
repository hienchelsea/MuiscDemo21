package com.example.cachua.musicdemo21.data.model

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore

data class MusicModel(
        val id: Int,
        val artist: String,
        val title: String,
        val data: String,
        val displayName: String,
        val duration: Int,
        val idAlbum: Long,
        val uri: Uri
) {
    constructor(cursor: Cursor) : this(
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)).toInt(),
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)).toInt(),
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toLong(),
            ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toLong())
    )
}
