package com.projecttesting.ui.base

import android.content.Context
import androidx.fragment.app.Fragment

/**
 * Base [Fragment] Class that binds [FragmentInteractionListener] when attached to activity.
 * Best use-case is a Fragment which is not injected from Dagger. For Dagger injected
 * Fragments see [BaseDaggerFragment].
 *
 */
open class BaseFragment : Fragment() {

    var mFragmentInteractionListener: FragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        mFragmentInteractionListener = context as? FragmentInteractionListener
        if (mFragmentInteractionListener == null) {
            throw ClassCastException("$context must implement FragmentInteractionListener")
        }
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        mFragmentInteractionListener = null
    }

}