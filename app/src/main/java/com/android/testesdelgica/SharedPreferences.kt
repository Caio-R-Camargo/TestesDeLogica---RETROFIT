package com.android.testesdelgica

import android.content.Context

class SharedPreferences(context: Context) {

    private val mSharedPreferences = context.getSharedPreferences("calculo", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        mSharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return mSharedPreferences.getString(key, "") ?: ""
    }

}