package net.matsudamper.kotlinpref

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build

open class KotlinPrefModel(private val context: Context, prefix: String = "") {
    open val fileName: String = "${prefix}-${javaClass.name}"
    open val mode: Int = Context.MODE_PRIVATE

    val functions = mutableMapOf<String, () -> Unit>()

    val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(fileName, mode).apply {
            registerOnSharedPreferenceChangeListener(listener)
        }
    }
    val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
        functions[key]?.invoke()
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun delete() {
        context.deleteSharedPreferences(fileName)
    }
}