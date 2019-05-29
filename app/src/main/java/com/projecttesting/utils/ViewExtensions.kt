package com.projecttesting.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.projecttesting.R


/**
 * Setup the visibility of the view depending on boolean value
 */
@BindingAdapter("android:isVisible")
fun View.setIsVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * Load image by url trough Glide
 */
fun ImageView.loadImage(url: String) = Glide.with(context).load(url).into(this)

/**
 * Check if permission is granted
 */
fun Context.hasPermission(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

/**
 * Return Main Navigation Controller
 */
fun Activity.getMainNavController() = findNavController(R.id.mainNavigationFragment)

/**
 * Return Main Navigation Controller
 */
fun Fragment.getMainNavController() = activity?.getMainNavController()

fun Fragment.backToPreviousScreen() = getMainNavController()?.navigateUp()

/**
 * Navigate via directions that describe this navigation operation, trough the main navigation controller
 */
fun Fragment.navigateTo(navDirections: NavDirections) {
    getMainNavController()?.navigate(navDirections)
}