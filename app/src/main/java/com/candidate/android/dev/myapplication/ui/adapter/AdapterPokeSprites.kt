package com.candidate.android.dev.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.candidate.android.dev.myapplication.R
import com.candidate.android.dev.myapplication.extension.loadFromURL
import kotlinx.android.synthetic.main.item_recycleview_poke_sprites.view.*

class AdapterPokeSprites : RecyclerView.Adapter<SpritesViewHolder>() {

    var pokeSpritesList: ArrayList<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpritesViewHolder {
        return SpritesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycleview_poke_sprites, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return pokeSpritesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: SpritesViewHolder, position: Int) {
        pokeSpritesList?.get(position)?.let { holder.bindSprites(it) }
    }

    fun setupSpritesData(data: ArrayList<String>) {
        this.pokeSpritesList = data
        notifyDataSetChanged()
    }

}

class SpritesViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun bindSprites(url:String){
        itemView.pokeDetailSprites.loadFromURL(url)
    }
}