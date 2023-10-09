package com.convertedin.task.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.convertedin.task.R
import com.convertedin.task.databinding.FragmentUserBinding
import com.convertedin.task.presentation.adapter.AlbumsAdapter
import com.convertedin.task.presentation.utils.ListItemClickListener
import com.convertedin.task.presentation.utils.RetryClickListener
import com.convertedin.task.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment : Fragment() {
    private val viewModel: UserViewModel by viewModels()

    private lateinit var binding: FragmentUserBinding

    @Inject
    lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.retryListener = RetryClickListener {
            viewModel.getAlbums()
        }
        binding.user = UserFragmentArgs.fromBundle(requireArguments()).user
        binding.lifecycleOwner = viewLifecycleOwner

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
        albumsAdapter.setOnClickListener(listener = ListItemClickListener {
            if (findNavController().currentDestination?.id == R.id.userFragment) {
                findNavController().navigate(
                    UserFragmentDirections.actionUserFragmentToUserPhotosFragment(
                        albumId = it.id
                    )
                )
            }
        })
        binding.albums.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = albumsAdapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.albums.collectLatest {
                    albumsAdapter.submitList(it)
                }
            }
        }
        return binding.root
    }
}