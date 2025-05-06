package com.myolwinoo.smartproperty.utils

import androidx.window.core.layout.WindowWidthSizeClass

object ColumnHelper {

    fun calculate(windowSizeClass: WindowWidthSizeClass): Int {
        return when (windowSizeClass) {
            WindowWidthSizeClass.EXPANDED -> 3
            WindowWidthSizeClass.COMPACT -> 1
            WindowWidthSizeClass.MEDIUM -> 2
            else -> 1
        }
    }
}