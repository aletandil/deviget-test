package com.projecttesting.ui.base

import android.content.Context
import dagger.android.support.DaggerFragment

/**
 * Base [DaggerFragment] Class that binds [FragmentInteractionListener] when attached to activity.
 * Best use-case is a Fragment which contains injected dependencies.
 *
 */
open class BaseDaggerFragment : DaggerFragment() {

    var mFragmentInteractionListener: FragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        mFragmentInteractionListener = context as? FragmentInteractionListener
        if (mFragmentInteractionListener == null) {
            throw ClassCastException("$context must implement FragmentInteractionListener")
        }
        super.onAttach(context)
    }
}