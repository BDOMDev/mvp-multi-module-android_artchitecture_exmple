package com.oshan.presentation.utils

import android.view.View

fun View.toggleVisibilityGone() {
    apply { visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE }
}

fun View.toggleVisibilityInvisible() {
    apply { visibility = if (visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE }
}