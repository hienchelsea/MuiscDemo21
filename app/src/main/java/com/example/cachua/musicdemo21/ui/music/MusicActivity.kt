package com.example.cachua.musicdemo21.ui.music.ui.music

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cachua.musicdemo21.R
import com.example.cachua.musicdemo21.data.model.MusicModel
import com.example.cachua.musicdemo21.data.repository.MusicRepository
import com.example.cachua.musicdemo21.data.source.ContentResolverData
import com.example.cachua.musicdemo21.data.source.MusicLocalDatasource
import com.example.cachua.musicdemo21.service.MyMusicService
import com.example.cachua.musicdemo21.ui.music.MusicContract
import com.example.cachua.musicdemo21.ui.music.MusicPresenter
import com.example.cachua.musicdemo21.ui.music.adapter.MusicAdapter
import com.example.cachua.musicdemo21.utils.Constant
import kotlinx.android.synthetic.main.activity_music.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MusicActivity : AppCompatActivity(), MusicContract.View, MyMusicService.CallBack, View.OnClickListener, MusicAdapter.OnItemClickListener {

    private lateinit var musicRepository: MusicRepository
    private lateinit var musicLocalDataSource: MusicLocalDatasource
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var myMusicService: MyMusicService
    private val musicPresenter: MusicContract.Presenter by lazy {
        MusicPresenter(musicRepository, this)
    }
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as MyMusicService.PlayMusicBinder
            myMusicService = binder.service()
            myMusicService.callBackService(this@MusicActivity)
        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        initData()
        initEvent()
    }

    private fun initData() {
        val contentProvider = ContentResolverData(this)
        musicLocalDataSource = MusicLocalDatasource(contentProvider)
        musicRepository = MusicRepository(musicLocalDataSource)
        simpleDateFormat = SimpleDateFormat("mm:ss", Locale.US)
        var intent = Intent(this, MyMusicService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        musicAdapter = MusicAdapter(this)
        recyclerMusic.run {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = musicAdapter
        }
    }

    private fun initEvent() {
        imagePlay.setOnClickListener(this)
        imagePlayNext.setOnClickListener(this)
        imagePlayBack.setOnClickListener(this)
        seekBarSong.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                myMusicService.onProgress(seekBarSong.progress)
            }
        })
    }

    override fun displayListMusic(arrayListMuisc: ArrayList<MusicModel>) {
        musicAdapter.upDateAdapter(arrayListMuisc)
    }

    override fun onError() {
        Toast.makeText(this, "Loi doc du lieu", Toast.LENGTH_LONG).show()
    }

    override fun loadMusic(music: ArrayList<MusicModel>, position: Int) {
        constraintPlay.visibility = View.VISIBLE
        textTitlePlay.text = music[position].title
        textArtistPlay.text = music[position].artist
        if (myMusicService.isPlaying() == 1) {
            imagePlay.setImageResource(R.drawable.ic_play_two)
        } else {
            imagePlay.setImageResource(R.drawable.ic_pause_two)
        }
        Glide
                .with(applicationContext)
                .load(music[position].uri)
                .placeholder(R.drawable.ic_music_player).into(imageAvatarPlay)
        textSongTotalDurationLabel.text = simpleDateFormat.format(music[position].duration.toLong())

        myMusicService.chooseMusic(this, music, position)
    }

    override fun onUpTime(music: MusicModel, progress: Int) {
        textTitlePlay.text = music.title
        textArtistPlay.text = music.artist
        Glide
                .with(applicationContext)
                .load(music.uri)
                .placeholder(R.drawable.ic_music_player).into(imageAvatarPlay)
        textSongTotalDurationLabel.text = simpleDateFormat.format(music.duration.toLong())
        textSongCurrentDurationLabel.text = simpleDateFormat.format(progress.toLong()).toString()
        var timeSong = (progress * 100 / music.duration)
        seekBarSong.progress = timeSong
    }

    private fun isRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    Constant.MY_PERMISSIONS_REQUEST_WRITE)
        } else {
            musicPresenter.loadDisPlayListMusic()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constant.MY_PERMISSIONS_REQUEST_WRITE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    musicPresenter.loadDisPlayListMusic()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isRequestPermissions()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imagePlay -> {
                if (myMusicService.isPlaying() == 0) {
                    imagePlay.setImageResource(R.drawable.ic_play_two)
                    myMusicService.pauseSong()
                } else {
                    imagePlay.setImageResource(R.drawable.ic_pause_two)
                    myMusicService.playSong()
                }
            }
            R.id.imagePlayNext -> myMusicService.nextSong()
            R.id.imagePlayBack -> myMusicService.previousSong()
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, MusicActivity::class.java)
    }
}
