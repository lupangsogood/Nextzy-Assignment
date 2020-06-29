package com.candidate.android.dev.myapplication.ui.main

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndexResult
import com.candidate.android.dev.myapplication.databinding.MainFragmentBinding
import com.candidate.android.dev.myapplication.extension.hideSoftKeyboard
import com.candidate.android.dev.myapplication.ui.adapter.AdapterMainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
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
            viewModel.pokemonCacheData.observe(viewLifecycleOwner, Observer { data ->
                if (data.isNullOrEmpty()) {
                    CoroutineScope(IO).launch {
                        viewModel.getPokemonList()
                    }
                } else {
                    viewModel.setUpPokeMainData(data)
                }
            })
            viewModel.pokemonOnceData.observe(viewLifecycleOwner, Observer { data ->
                if (!data.isNullOrEmpty()) {
                    viewModel.setUpPokeMainData(data)
                }
            })
        }
        initInstance()

    }

    private fun initInstance() {
        viewModel.isShowRefresh()
        listenerEditText()
        setupSwipeRefresh()
        setOnclickShowDetail()
        setMainAdapter()
        setAutoCompleteItem()

        viewModel.pokemonList.observe(viewLifecycleOwner, Observer { data ->

            when (!data.isNullOrEmpty()) {
                true -> {
                    setupPokeData(data)
                }
            }
        })

        viewModel.refresh.observe(viewLifecycleOwner, Observer { isShow ->
            binding.swipeRefresh.isRefreshing = isShow
        })

        viewModel.scroll.observe(viewLifecycleOwner, Observer { isScroll ->
            binding.rvMain.isVerticalScrollBarEnabled = isScroll
        })
    }

    private fun setMainAdapter() {
        val rvMain = binding.rvMain
        rvMain.adapter = adapter
        rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.isShowRefresh()
                    viewModel.isCanNotScroll()
                    CoroutineScope(IO).launch {
                        viewModel.getNextPagePokemon()
                    }
                }
            }
        })
    }

    private fun setupPokeData(pokedDataList: List<PokeIndexResult>) {
        val flag = viewModel.isSearch
        adapter.setPokeData(pokedDataList)
        viewModel.isCanScroll()
        viewModel.isNotShowRefresh()
        viewModel.isNotBackPress()
        viewModel.isNotPullRefresh()
        when (flag) {
            true -> {
                binding.rvMain.scrollToPosition(0)
            }
            false -> {
            }
        }
    }

    private fun setOnclickShowDetail() {
        adapter.setShowDetailCallback {
            viewModel.isBackPress()
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment2(
                pokemonId = it
            )
            findNavController().navigate(action)
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            clearAutoCompText()
            viewModel.isNotBackPress()
            viewModel.isShowRefresh()
            viewModel.isPullRefresh()
            CoroutineScope(IO).launch {
                viewModel.getRefreshPokemon()
            }
        }
    }

    private fun listenerEditText() {

        binding.searchEditText.setOnEditorActionListener { txtView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    viewModel.setupPokeNameToGetData("${txtView.text}")
                    hideSoftKeyboard()
                    clearEditTextFocus()
                    return@setOnEditorActionListener true
                }
                else -> return@setOnEditorActionListener false
            }
        }
    }

    private fun setAutoCompleteItem() {
        viewModel.pokemonNameCacheData.observe(viewLifecycleOwner, Observer {
            binding.searchEditText.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.simple_dropdown_item_1line,
                    it
                )
            )

            binding.searchEditText.setOnItemClickListener { parent, view, position, id ->
                viewModel.isShowRefresh()
                hideSoftKeyboard()
                clearEditTextFocus()
                viewModel.setupPokeNameToGetData(it[position])
            }
        })

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setupNameToComplete("$s")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setupNameToComplete("$s")
            }

        })
    }

    private fun clearAutoCompText() {
        binding.searchEditText.text.clear()
    }

    private fun clearEditTextFocus() {
        binding.searchEditText.clearFocus()
    }
}