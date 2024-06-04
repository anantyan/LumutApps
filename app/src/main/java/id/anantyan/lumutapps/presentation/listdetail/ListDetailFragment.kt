package id.anantyan.lumutapps.presentation.listdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.lumutapps.common.UIState
import id.anantyan.lumutapps.data.remote.network.AppNetwork
import id.anantyan.lumutapps.data.remote.service.TodoApi
import id.anantyan.lumutapps.data.repository.TodoRepository
import id.anantyan.lumutapps.databinding.FragmentListDetailBinding
import id.anantyan.lumutapps.di.ListDetailFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by Arya Rezza Anantya on 04/06/24.
 */
class ListDetailFragment : Fragment() {

    private var _binding: FragmentListDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListDetailViewModel by viewModels {
        ListDetailFactory(TodoRepository(AppNetwork.provideApi(requireContext()).create(TodoApi::class.java)))
    }
    private val args: ListDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindInit()
        bindObserver()
    }

    private fun bindInit() {
        binding.toolbar.setOnClickListener { findNavController().navigateUp() }
    }

    private fun bindObserver() {
        viewModel.result.onEach { state ->
            when (state) {
                is UIState.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is UIState.Success -> {
                    binding.txtTitle.text = state.data?.title
                }
                is UIState.Error -> {
                    Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
        viewModel.result(args.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}