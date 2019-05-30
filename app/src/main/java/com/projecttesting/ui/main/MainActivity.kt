package com.projecttesting.ui.main

import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.projecttesting.R
import com.projecttesting.ui.base.FragmentInteractionListener
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

/**
 * DaggerAppCompatActivity handles dependency injection
 */
class MainActivity : DaggerAppCompatActivity(), FragmentInteractionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        setContentView(R.layout.activity_main)

    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.mainNavigationFragment).navigateUp()

    override fun onFragmentInteraction(uri: Uri) {
        Timber.d("uri path -> " + uri.path)
    }

}
