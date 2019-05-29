package com.projecttesting.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.projecttesting.R
import com.projecttesting.databinding.FragmentMainBinding
import com.projecttesting.domain.LoadTopEntriesUseCaseResult
import com.projecttesting.ui.base.BaseDaggerFragment
import javax.inject.Inject

/**
 * Example Fragment
 */
class MainFragment : BaseDaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // example shared viewModel with MainActivity
        mainViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainViewModel::class.java)

        // FragmentMainBinding is generated from R.layout.fragment_main
        val binding = FragmentMainBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@MainFragment)
            viewModel = this@MainFragment.mainViewModel  // equivalent to java setViewModel()
        }

        mainViewModel.topEntries.observe(this, Observer<LoadTopEntriesUseCaseResult> { topEntriesResult ->
            when (topEntriesResult) {
                is LoadTopEntriesUseCaseResult.LoadTopEntriesSuccessful -> binding.viewModel?.name?.set("asd")
                is LoadTopEntriesUseCaseResult.LoadTopEntriesError -> showErrorLoadingTopEntries()
            }
        })

        return binding.root
    }

    private fun showErrorLoadingTopEntries() {
        Toast.makeText(context, R.string.error_loading_top_entries, Toast.LENGTH_SHORT).show()
    }

}
