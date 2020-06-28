package com.candidate.android.dev.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.candidate.android.dev.myapplication.R
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndexResult
import com.candidate.android.dev.myapplication.data.PokemonArrayList
import com.candidate.android.dev.myapplication.extension.loadFromURL
import com.candidate.android.dev.myapplication.util.Constant
import kotlinx.android.synthetic.main.item_recycle_main.view.*

class AdapterMainScreen : RecyclerView.Adapter<MainScreenViewHolder>() {

    private var pokeDataList = PokemonArrayList()
    private var detailCallback :((position:Int)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScreenViewHolder {
        return MainScreenViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_main, parent, false)
        )
    }



    override fun getItemCount(): Int {
        return pokeDataList.size
    }

    override fun onBindViewHolder(holder: MainScreenViewHolder, position: Int) {
        val data = pokeDataList[position]
        data.let { holder.bindCharacter(it, position.plus(1)) }
        holder.itemView.setOnClickListener {
            detailCallback?.invoke(position.plus(1))
        }
    }

    fun setPokeData(dataList: PokemonArrayList,clearData:Boolean) {
        this.pokeDataList.clear()
        this.pokeDataList.addAll(dataList!!)
        notifyDataSetChanged()
    }

    fun setShowDetailCallback(block:(position:Int)->Unit){
        this.detailCallback = block
    }
}

class MainScreenViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    fun bindCharacter(data: PokeIndexResult, position: Int) {
        itemView.let {
            it.pokeName.text = data.name
            it.pokeImage.loadFromURL(Constant.getPokemonImage("$position"))
        }
    }
}