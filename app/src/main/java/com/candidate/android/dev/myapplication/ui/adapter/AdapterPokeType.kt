package com.candidate.android.dev.myapplication.ui.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.candidate.android.dev.myapplication.R
import com.candidate.android.dev.myapplication.data.Model.PokeDetail.Type
import kotlinx.android.synthetic.main.item_recycleview_poke_type.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterPokeType : RecyclerView.Adapter<TypeViewHolder>() {

    var pokeTypesList: ArrayList<Type>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        return TypeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycleview_poke_type, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return pokeTypesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        val data = pokeTypesList?.get(position)
        data?.type?.name?.let { holder.bindPokeTypes(it) }
    }

    fun setPokeTypes(data: ArrayList<Type>) {
        this.pokeTypesList = data
        notifyDataSetChanged()
    }
}

class TypeViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun bindPokeTypes(type: String) {
        itemView.context.let {
            val colorId: Int = it.resources.getIdentifier(type, "color", it.packageName)
            itemView.typeTitle.setBackgroundColor(it.getColor(colorId))
            itemView.typeTitle.text = type.toUpperCase(Locale.ROOT)
        }
    }
}