package com.furkanbostan.moneymanagement.ui

import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseCoroutine():CoroutineScope,Fragment() {

    private val job= Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


}