package com.furkanbostan.moneymanagement.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.time.DayOfWeek
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

val locale = Locale.forLanguageTag("tr-TR")
internal fun Context.getColorCompat(@ColorRes color: Int) =
    ContextCompat.getColor(this, color)


internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(context.getColorCompat(color))

fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, locale)
}

fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, locale).let { value ->
        if (uppercase) value.uppercase(locale) else value
    }
}

/**
 * Extension function to get [InputMethodManager]
 */
fun Context.getInputMethodManager() =
    this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

/**
 * This method used to hide keyboard if the keyboard is showing on the screen
 */

fun Activity.hideKeyBoard() {
    var view: View? = currentFocus
    if (view == null) {
        view = View(this)
    }
    getInputMethodManager().hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * This method used to hide keyboard if the keyboard is showing on the screen
 */
fun Fragment.hideKeyBoard() {
    if (isAdded) {
        var view: View? = requireActivity().currentFocus

        if (view == null) {
            view = View(requireContext())
        }
        requireContext()
            .getInputMethodManager()
            .hideSoftInputFromWindow(view.windowToken, 0)
    }
}