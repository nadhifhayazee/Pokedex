package com.nadhifhayazee.pokedex.list_pokemon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.nadhifhayazee.core_model.State
import com.nadhifhayazee.pokedex.baseview.BaseFragment
import com.nadhifhayazee.pokedex.databinding.FragmentListPokemonBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListPokemonFragment :
    BaseFragment<FragmentListPokemonBinding>(FragmentListPokemonBinding::inflate) {

    private val viewModel by viewModels<ListPokemonViewModel>()
    private val adapter by lazy {
        ListPokemonAdapter() { pokemonResult ->

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observeVm()
        viewModel.getPokemonList()
    }

    private fun setupView() {
        binding.apply {
            rvPokemon.layoutManager = GridLayoutManager(requireContext(), 2)
            rvPokemon.adapter = adapter
        }
    }

    private fun observeVm() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pokemonListState.collectLatest { state ->
                    when (state) {
                        is State.Success -> {
                            adapter.submitData(state.data)
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}