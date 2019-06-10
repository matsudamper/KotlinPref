package net.matsudamper.kotlinpref.pref

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.matsudamper.kotlinpref.KotlinPref
import net.matsudamper.kotlinpref.KotlinPrefModel
import kotlin.reflect.KProperty


@Suppress("NOTHING_TO_INLINE")
inline fun KotlinPrefModel.stringPref(
        default: String = "",
        key: String? = null
) = StringPref(default, key)

class StringPref(
        private val default: String = "",
        private val key: String? = null
) : KotlinPref<String>() {
    override fun write(property: KProperty<*>, value: String, preference: SharedPreferences) {
        preference.edit {
            putString(key ?: property.name, value)
        }
    }

    override fun read(property: KProperty<*>, preference: SharedPreferences): String {
        return preference.getString(key ?: property.name, default)!!
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun KotlinPrefModel.stringPrefLiveData(
        default: String = "",
        key: String
): LiveData<String> {
    val liveData = MutableLiveData<String>()
    functions[key] = {
        liveData.postValue(sharedPreferences.getString(key, default))
    }.apply { invoke() }
    return liveData
}