package com.user.profile.data

import kotlin.math.round

class WeightConverter {

    private companion object {
        const val MULTI = 0.453592f
    }

    fun convertLbToKg(lb: Int): Int {
        return round(lb * MULTI).toInt()
    }

    fun convertKgToLb(kg: Int): Int {
        return round(kg / MULTI).toInt()
    }
}
