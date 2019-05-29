package com.projecttesting.ui.base

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Base [DialogFragment] Class that binds [FragmentInteractionListener] when attached to activity.
 * Best use-case is a Dialog Fragment which is not injected from Dagger. For Dagger injected
 * Dialog Fragments see [BaseDaggerDialogFragment].
 *
 */
abstract class BaseDialogFragment<T : ViewDataBinding> : DialogFragment() {

    var mFragmentInteractionListener: FragmentInteractionListener? = null

    lateinit var binding: T

    /**
     * Creates dialog, and initializes data binding variable.
     *
     * @param width Width of container.
     * @param height Height of container.
     * @param features List of features to add to [Window] (empty by default).
     */
    fun getDialog(width: Int, height: Int, features: Array<Int> = arrayOf()): Dialog {

        val inflater = requireActivity().layoutInflater
        val layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val container = ConstraintLayout(requireContext())
        container.layoutParams = layoutParams

        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)

        val dialog = Dialog(context!!)

        val window = dialog.window

        features.forEach { feature ->
            window?.requestFeature(feature)
        }

        dialog.setContentView(binding.root)

        window?.setLayout(width, height)

        return dialog
    }

    /**
     * Shared functionality between each [BaseDaggerDialogFragment] to show dialog.
     */
    fun showDialog(activity: FragmentActivity, tag: String) {
        val manager = activity.supportFragmentManager
        val transaction = manager.beginTransaction()
        val previousFragment: Fragment? = manager.findFragmentByTag(tag)

        previousFragment?.let { transaction.remove(it) }

        transaction.addToBackStack(null)

        show(transaction, tag)
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

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