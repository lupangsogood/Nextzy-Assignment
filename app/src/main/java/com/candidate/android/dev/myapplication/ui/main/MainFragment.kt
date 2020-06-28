package com.candidate.android.dev.myapplication.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.candidate.android.dev.myapplication.R
import com.candidate.android.dev.myapplication.data.PokemonArrayList
import com.candidate.android.dev.myapplication.databinding.MainFragmentBinding
import com.candidate.android.dev.myapplication.extension.hideSoftKeyboard
import com.candidate.android.dev.myapplication.ui.adapter.AdapterMainScreen
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModel()

    val adapter = AdapterMainScreen()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getPokemonList()
        }
        initInstance()
    }

    private fun initInstance() {
        binding.swipeRefresh.isRefreshing = true
        listenerEditText()
        setupSwipeRefresh()
        setOnclickShowDetail()
        setMainAdapter()

        viewModel.pokemonList.observe(viewLifecycleOwner, Observer { data ->
            when (!data.isNullOrEmpty()) {
                true -> {
                    if (!viewModel.backPress){
                        setupPokeData(data)
                    }else{
                        binding.swipeRefresh.isRefreshing = false
                    }
                }
            }
        })
    }

    private fun setMainAdapter() {
        val rvMain = binding.rvMain
        rvMain.adapter = adapter
        rvMain.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    setupScroll(false)
                    CoroutineScope(IO).launch {
                        viewModel.getNextPagePokemon()
                    }
                }
            }
        })
    }

    private fun setupPokeData(pokedDataList: PokemonArrayList) {
        adapter.setPokeData(pokedDataList, viewModel.clearData)
        setupScroll(false)
    }

    private fun setOnclickShowDetail() {
        adapter.setShowDetailCallback {
            viewModel.backPress = true
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment2(
                pokemonId = it
            )
            findNavController().navigate(action)
        }
    }

    private fun setupScroll(canScroll: Boolean) {
        binding.swipeRefresh.isRefreshing = canScroll
        binding.rvMain.isVerticalScrollBarEnabled = canScroll
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            CoroutineScope(IO).launch {
                viewModel.getPokemonList()
            }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun listenerEditText() {

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    hideSoftKeyboard()
                    binding.searchEditText.clearFocus()
                    return@setOnEditorActionListener true
                }
                else -> return@setOnEditorActionListener false
            }
        }
    }
}