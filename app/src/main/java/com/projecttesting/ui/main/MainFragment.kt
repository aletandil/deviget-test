package com.projecttesting.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.projecttesting.data.models.Entry
import com.projecttesting.databinding.FragmentMainBinding
import com.projecttesting.domain.LoadTopEntriesUseCaseResult
import com.projecttesting.ui.base.BaseDaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


/**
 * Example Fragment
 */
class MainFragment : BaseDaggerFragment() {

    enum class ActionType {
        OPEN_ENTRY,
        DISMISS_ENTRY
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    @SuppressLint("NewApi")
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

            recyclerEntries.layoutManager = LinearLayoutManager(context)
            recyclerEntries.adapter = EntriesAdapter(listOf(), { entry, actionType ->
                when (actionType) {
                    ActionType.OPEN_ENTRY -> {
                        mainViewModel.markReadedEntry(entry)
                    }

                    ActionType.DISMISS_ENTRY -> {
                        mainViewModel.dismissEntry(entry)
                    }
                }

            }, mainViewModel.entryRepository)
            recyclerEntries.setOnScrollChangeListener { _, _, _, _, _ ->
                if (!recyclerEntries.canScrollVertically(1)) {
                    // reaches the bottom of the scroll
                    mainViewModel.loadMoreEntries()
                }
            }
        }

        mainViewModel.topEntries.observe(this, Observer<LoadTopEntriesUseCaseResult> { topEntriesResult ->
            when (topEntriesResult) {
                is LoadTopEntriesUseCaseResult.LoadTopEntriesSuccessful -> {
                    //hide loading
                }
                is LoadTopEntriesUseCaseResult.LoadTopEntriesError -> showErrorLoadingTopEntries()
            }
        })

        mainViewModel.getLocalTopEntries().observe(this, Observer<List<Entry>> { entries ->
            (recycler_entries.adapter as? EntriesAdapter)?.updateEntries(entries)
            recycler_entries.layoutManager?.onRestoreInstanceState(mainViewModel.entriesScrollState)
        });

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.entriesScrollState = recycler_entries.layoutManager?.onSaveInstanceState()
    }

    private fun showErrorLoadingTopEntries() {
        Toast.makeText(context, com.projecttesting.R.string.error_loading_top_entries, Toast.LENGTH_SHORT).show()
    }

}
