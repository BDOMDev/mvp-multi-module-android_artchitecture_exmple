package com.oshan.presentation.utils

import android.content.Context
import android.content.res.Resources
import androidx.annotation.Dimension


object Helpers {

    /**
     * Returns the width of the screen
     *
     * @return int screen width (pixels)
     */
    val screenWidth: Int
        get() = Resources.getSystem().displayMetrics.widthPixels

    /**
     * Returns the height of the screen
     *
     * @return int screen height (pixels)
     */
    val screenHeight: Int
        get() = Resources.getSystem().displayMetrics.heightPixels

}