package id.anantyan.lumutapps.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.lumutapps.common.UIState
import id.anantyan.lumutapps.data.remote.network.AppNetwork
import id.anantyan.lumutapps.data.remote.service.TodoApi
import id.anantyan.lumutapps.data.repository.TodoRepository
import id.anantyan.lumutapps.databinding.FragmentListBinding
import id.anantyan.lumutapps.di.ListFactory
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by Arya Rezza Anantya on 04/06/24.
 */
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by viewModels {
        ListFactory(TodoRepository(AppNetwork.provideApi(requireContext()).create(TodoApi::class.java)))
    }
    private val adapter: ListViewAdapter by lazy { ListViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindInit()
        bindObserver()
    }

    private fun bindObserver() {
        viewModel.results.onEach { state ->
            when (state) {
                is UIState.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is UIState.Success -> {
                    Log.d("List", state.data.toString())
                    adapter.submitList(state.data)
                }
                is UIState.Error -> {
                    Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)

        viewModel.results()
    }

    private fun bindInit() {
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvList.adapter = adapter
        adapter.onClick { position, item ->
            findNavController().navigate(ListFragmentDirections.actionListFragmentToListDetailFragment(item.id ?: -1))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}