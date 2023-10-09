package com.convertedin.task.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.convertedin.task.R
import com.convertedin.task.databinding.FragmentHomeBinding
import com.convertedin.task.presentation.adapter.UsersAdapter
import com.convertedin.task.presentation.utils.ListItemClickListener
import com.convertedin.task.presentation.utils.RetryClickListener
import com.convertedin.task.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var usersAdapter: UsersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.retryListener = RetryClickListener {
            viewModel.getUsers()
        }
        binding.lifecycleOwner = viewLifecycleOwner

        usersAdapter.setOnClickListener(listener = ListItemClickListener {
            if (findNavController().currentDestination?.id == R.id.homeFragment) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToUserFragment(user = it)
                )
            }
        })

        binding.users.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collectLatest {
                    usersAdapter.submitList(it)
                }
            }
        }
        return binding.root
    }
}