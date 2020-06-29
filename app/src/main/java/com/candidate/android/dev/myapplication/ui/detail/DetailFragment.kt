package com.candidate.android.dev.myapplication.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.candidate.android.dev.myapplication.data.Model.PokeDetail.PokeDetail
import com.candidate.android.dev.myapplication.data.Model.PokeDetail.Sprites
import com.candidate.android.dev.myapplication.data.Model.PokeDetail.Stat
import com.candidate.android.dev.myapplication.data.Model.PokeDetail.Type
import com.candidate.android.dev.myapplication.databinding.DetailFragmentBinding
import com.candidate.android.dev.myapplication.ui.adapter.AdapterPokeSprites
import com.candidate.android.dev.myapplication.ui.adapter.AdapterPokeStats
import com.candidate.android.dev.myapplication.ui.adapter.AdapterPokeType
import com.candidate.android.dev.myapplication.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModel()

    var pokemonId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let { bundle ->
            val args = DetailFragmentArgs.fromBundle(bundle)
            this.pokemonId = args.pokemonId
        }

        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getPokemonDetail("$pokemonId")
        }
        initInstance()
    }

    private fun initInstance() {

        viewModel.pokemonDetail.observe(viewLifecycleOwner, Observer { data ->
            when (data != null) {
                true -> {
                    setPokeNameHeight(data)
                    data.sprites?.let { setupSpritesAdapter(it) }
                    data.types?.let { setupTypesAdapter(it) }
                    data.stats?.let { setupStatsAdapter(it) }
                }
                false -> {
                }
            }
        })

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupSpritesAdapter(data: Sprites) {
        val listOfSprites = arrayListOf(data.front_default ?: "", data.back_default ?: "")
        val adapterSprites = AdapterPokeSprites()
        val rv = binding.rvPokeDetailSprites
        rv.adapter = adapterSprites
        rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapterSprites.setupSpritesData(listOfSprites)
    }

    private fun setupTypesAdapter(data: ArrayList<Type>) {
        val adapterTypes = AdapterPokeType()
        val rv = binding.rvPokeDetailType
        rv.adapter = adapterTypes
        adapterTypes.setPokeTypes(data)
    }

    @SuppressLint("SetTextI18n")
    private fun setPokeNameHeight(data: PokeDetail) {
        binding.pokeDetailName.text = "${data.name}"
        binding.pokeDetailWeight.text = "Weight : ${data.weight} "
        binding.pokeDetailHeight.text = "Height : ${data.height}"
    }

    private fun setupStatsAdapter(data: ArrayList<Stat>) {
        val adapterStats = AdapterPokeStats()
        val rv = binding.rvPokeDetailStats
        rv.adapter = adapterStats
        rv.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        adapterStats.setupStatsData(data)
    }

}