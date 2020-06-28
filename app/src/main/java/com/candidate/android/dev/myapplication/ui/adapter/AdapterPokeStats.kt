package com.candidate.android.dev.myapplication.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.candidate.android.dev.myapplication.R
import com.candidate.android.dev.myapplication.data.Model.PokeDetail.Stat
import kotlinx.android.synthetic.main.item_recycle_main.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterPokeStats :RecyclerView.Adapter<PokeTypeViewHolder>(){

    var pokeStatsList : ArrayList<Stat>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeTypeViewHolder {
      return PokeTypeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_main,parent,false))
    }

    override fun getItemCount(): Int {
        return pokeStatsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: PokeTypeViewHolder, position: Int) {
        val data = pokeStatsList?.get(position)
        holder.bindPokeStats("${data?.base_stat}",data?.stat?.name ?: "")
    }

    fun setupStatsData(data : ArrayList<Stat>){
        this.pokeStatsList = data
        notifyDataSetChanged()
    }

}



class PokeTypeViewHolder(v: View):RecyclerView.ViewHolder(v){
    fun bindPokeStats(stats:String, statsTitle:String){
        itemView.let {
            it.pokeImage.visibility = View.GONE
            it.pokeStat.visibility = View.VISIBLE
            it.pokeStat.text = stats
            it.pokeName.text = statsTitle.toUpperCase(Locale.getDefault())
            it.pokeName.textSize = 12.0f
        }
    }
}