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
import com.projecttesting.data.models.Rider
import com.projecttesting.databinding.FragmentMainBinding
import com.projecttesting.domain.LoadRiderUseCaseResult
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

        mainViewModel.rider.observe(this, Observer<LoadRiderUseCaseResult> { riderResult ->
            when (riderResult) {
                is LoadRiderUseCaseResult.LoadRiderSuccessful -> binding.viewModel?.name?.set(riderResult.rider.firstName)
                is LoadRiderUseCaseResult.LoadRiderError -> showErrorLoadingRider()
            }
        })

        return binding.root
    }

    private fun showErrorLoadingRider() {
        Toast.makeText(context, R.string.error_loading_rider, Toast.LENGTH_SHORT).show()
    }

}
