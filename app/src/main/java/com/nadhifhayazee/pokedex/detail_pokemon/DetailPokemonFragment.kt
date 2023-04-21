package com.nadhifhayazee.pokedex.detail_pokemon

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.nadhifhayazee.core_data.util.MyPokemonUtil
import com.nadhifhayazee.core_model.Pokemon
import com.nadhifhayazee.core_model.State
import com.nadhifhayazee.pokedex.R
import com.nadhifhayazee.pokedex.baseview.BaseFragment
import com.nadhifhayazee.pokedex.baseview.NicknameDialog
import com.nadhifhayazee.pokedex.databinding.FragmentDetailPokemonBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailPokemonFragment :
    BaseFragment<FragmentDetailPokemonBinding>(FragmentDetailPokemonBinding::inflate) {

    private var pokemonName: String? = null
    private var pokemon: Pokemon? = null

    private val viewModel by viewModels<DetailPokemonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokemonName = it.getString(POKEMON_NAME)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewListener()
        observeVm()
        observeCatchPokemon()
        observeIsMyPokemon()

    }

    private fun setupViewListener() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun observeCatchPokemon() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.catchPokemonState.collectLatest {
                    when (it) {
                        is State.Success -> {
                            Toast.makeText(activity, "Catch Pokemon Success", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun observeVm() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailPokemon.collectLatest { state ->
                    when (state) {
                        is State.Init -> {
                            pokemonName?.let { viewModel.getDetailPokemon(it) }
                        }
                        is State.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is State.Success -> {
                            binding.progressBar.visibility = View.GONE
                            bindView(state.data)
                        }
                        is State.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(activity, state.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun bindView(data: Pokemon) {
        pokemon = data
        binding.apply {
            tvName.text = data.name
            ivPokemon.load(MyPokemonUtil.getImageUrl(data.id))
            tvHeight.text = "${data.height} M"
            tvWeight.text = "${data.weight} KG"
            tvTypes.text = data.getTypesToString()
            tvMoves.text = data.getMovesToString()

            data.id?.let { viewModel.checkIsMyPokemon(it) }

        }
    }

    private fun observeIsMyPokemon() {
        viewModel.isMyPokemon.observe(viewLifecycleOwner) {
            setupMyPokemonState(it)
        }
    }

    private fun setupMyPokemonState(isMyPokemon: Boolean) {
        binding.apply {
            if (isMyPokemon) {
                btnCatchPokemon.text = getString(R.string.release_pokemon)
                btnCatchPokemon.background = activity?.let {
                    ContextCompat.getDrawable(
                        it, R.drawable.bg_release_pokemon_btn
                    )
                }
                activity?.let { btnCatchPokemon.setTextColor(it.getColor(R.color.purple_700)) }
                btnCatchPokemon.setOnClickListener {
                    releasePokemon()
                }
            } else {
                btnCatchPokemon.text = getString(R.string.catch_pokemon)
                btnCatchPokemon.background = activity?.let {
                    ContextCompat.getDrawable(
                        it, R.drawable.bg_catch_pokemon_btn
                    )
                }
                activity?.let { btnCatchPokemon.setTextColor(it.getColor(R.color.white)) }
                btnCatchPokemon.setOnClickListener {
                    catchPokemon()
                }
            }
        }
    }

    private fun releasePokemon() {
        pokemon?.id?.let { viewModel.releasePokemon(it) }
    }

    private fun catchPokemon() {
        if (viewModel.getPossibility()) {
            showAddNicknameDialog()
        } else {
            Toast.makeText(activity, "Try again later", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAddNicknameDialog() {
        activity?.let { ctx ->

            val dialog = NicknameDialog(ctx) { nickname ->
                pokemon?.let { viewModel.catchPokemon(it, nickname) }
            }
            dialog.show()
        }
    }

    companion object {
        private const val POKEMON_NAME = "POKEMON_NAME"
        fun navigate(name: String, doNavigate: (Bundle) -> Unit) {
            val bundle = Bundle()
            bundle.putString(POKEMON_NAME, name)
            doNavigate.invoke(bundle)
        }
    }

}