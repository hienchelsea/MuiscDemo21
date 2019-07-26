package com.example.cachua.musicdemo21.ui.music.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.cachua.musicdemo21.R
import com.example.cachua.musicdemo21.data.model.MusicModel
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.android.synthetic.main.notification_layout.view.*

class MusicAdapter(
        val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    private val musics = ArrayList<MusicModel>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_layout, viewGroup, false)
        return ViewHolder(itemView, onItemClickListener)
    }

    override fun getItemCount(): Int = musics.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
       viewHolder.onBindData(musics,position,onItemClickListener)
    }

    fun upDateAdapter(newMusics: List<MusicModel>) {
        musics.clear()
        musics.addAll(newMusics)
        notifyDataSetChanged()
    }


    class ViewHolder(
            itemView: View,
            val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        fun onBindData(musics: ArrayList<MusicModel>, position: Int, onItemClickListener: OnItemClickListener) {
            val music = musics[position]

            itemView.run {
                textTitlePlay.text = music.title
                textArtistPlay.text = music.artist

                Glide.with(context)
                        .load((music.uri))
                        .placeholder(R.drawable.ic_music_player).into(imageAvatarPlay)

                constraintItem.setOnClickListener {
                    onItemClickListener.loadMusic(musics, position)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun loadMusic(music: ArrayList<MusicModel>, position: Int)
    }
}
