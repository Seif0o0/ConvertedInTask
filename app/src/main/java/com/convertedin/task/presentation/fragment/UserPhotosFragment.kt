package com.convertedin.task.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.convertedin.task.databinding.FragmentUserPhotosBinding
import com.convertedin.task.presentation.adapter.PhotosAdapter
import com.convertedin.task.presentation.utils.ListItemClickListener
import com.convertedin.task.presentation.utils.RetryClickListener
import com.convertedin.task.presentation.viewmodel.UserPhotosViewModel
import com.convertedin.task.utils.getScreenSize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserPhotosFragment : Fragment() {
    private val viewModel: UserPhotosViewModel by viewModels()

    private lateinit var binding: FragmentUserPhotosBinding

    @Inject
    lateinit var photosAdapter: PhotosAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserPhotosBinding.inflate(inflater, container, false)
        var screenSize = if (Build.VERSION.SDK_INT < 30) getScreenSize(resources = resources)
        else getScreenSize(
            resources = resources,
            windowMetrics = requireContext().applicationContext.getSystemService(
                WindowManager::class.java
            ).maximumWindowMetrics
        )
        binding.viewModel = viewModel
        binding.retryListener = RetryClickListener {
            viewModel.getPhotos()
        }
        binding.lifecycleOwner = viewLifecycleOwner

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        photosAdapter.setOnClickListener(listener = ListItemClickListener {

        })

        binding.photos.apply {
            val spanCount = if (screenSize.first < 600) 3 else if (screenSize.first < 840) 5 else 7
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = photosAdapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.photos.collectLatest {
                    photosAdapter.submitList(it)
                }
            }
        }

        viewModel.search.observe(viewLifecycleOwner) { query ->
            val list = viewModel.photos.value.filter { it.title.contains(query) }
            if (list.isEmpty()) {
                viewModel.updateEmptyVisibilityValue(View.VISIBLE)
            } else {
                viewModel.updateEmptyVisibilityValue(View.GONE)
            }
            photosAdapter.submitList(list)
        }
        return binding.root
    }
}