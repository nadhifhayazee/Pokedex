package com.nadhifhayazee.pokedex.my_pokemon

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nadhifhayazee.core_model.State
import com.nadhifhayazee.pokedex.R
import com.nadhifhayazee.pokedex.baseview.BaseFragment
import com.nadhifhayazee.pokedex.databinding.FragmentMyPokemonBinding
import com.nadhifhayazee.pokedex.detail_pokemon.DetailPokemonFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyPokemonFragment :
    BaseFragment<FragmentMyPokemonBinding>(FragmentMyPokemonBinding::inflate) {

    private val viewModel by viewModels<MyPokemonViewModel>()

    private val myPokemonAdapter by lazy {
        MyPokemonAdapter(onItemClicked = { name ->
            navigateToDetailPokemon(name)
        },
            onReleaseClicked = { id ->
                viewModel.releasePokemon(
                    id
                )
            },
            onRenameClicked = { id ->
                viewModel.renamePokemon(id)
            })
    }

    private fun navigateToDetailPokemon(name: String) {
        DetailPokemonFragment.navigate(name) { bundle ->
            findNavController().navigate(
                R.id.action_fragmentMyPokemon_to_fragmentDetailPokemon,
                bundle
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observeVm()

        viewModel.getMyPokemon()
    }

    private fun observeVm() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myPokemonState.collectLatest {
                    when (it) {
                        is State.Init -> {
                        }
                        is State.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is State.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvDataEmpty.isVisible = it.data.isEmpty()
                            binding.rvPokemon.isVisible = it.data.isNotEmpty()
                            myPokemonAdapter.submitList(it.data)
                        }
                        is State.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(activity, it.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupView() {
        binding.apply {
            btnBack.setOnClickListener { findNavController().popBackStack() }

            rvPokemon.layoutManager = LinearLayoutManager(requireContext())
            rvPokemon.adapter = myPokemonAdapter
        }
    }

}