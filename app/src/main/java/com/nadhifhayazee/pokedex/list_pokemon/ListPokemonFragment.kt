package com.nadhifhayazee.pokedex.list_pokemon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nadhifhayazee.core_model.PokemonResult
import com.nadhifhayazee.core_model.State
import com.nadhifhayazee.pokedex.R
import com.nadhifhayazee.pokedex.baseview.BaseFragment
import com.nadhifhayazee.pokedex.databinding.FragmentDetailPokemonBinding
import com.nadhifhayazee.pokedex.databinding.FragmentListPokemonBinding
import com.nadhifhayazee.pokedex.detail_pokemon.DetailPokemonFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListPokemonFragment :
    BaseFragment<FragmentListPokemonBinding>(FragmentListPokemonBinding::inflate) {

    private val viewModel by viewModels<ListPokemonViewModel>()
    private val adapter by lazy {
        ListPokemonAdapter() { pokemonResult ->
            navigateToDetailPokemon(pokemonResult)
        }
    }

    private fun navigateToDetailPokemon(pokemonResult: PokemonResult) {
        pokemonResult.name?.let {
            DetailPokemonFragment.navigate(it) { bundle ->
                findNavController().navigate(
                    R.id.action_fragmentListPokemon_to_fragmentDetailPokemon,
                    bundle
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observeVm()
    }

    private fun setupView() {
        binding.apply {
            rvPokemon.layoutManager = GridLayoutManager(requireContext(), 2)
            rvPokemon.adapter = adapter

            tvMyPokemon.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentListPokemon_to_fragmentMyPokemon)
            }
        }
    }

    private fun observeVm() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pokemonListState.collectLatest { state ->
                    when (state) {
                        is State.Init -> {
                            viewModel.getPokemonList()
                        }
                        is State.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is State.Success -> {
                            binding.progressBar.visibility = View.GONE
                            adapter.submitData(state.data)
                        }
                        else -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}