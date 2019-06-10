package net.matsudamper.kotlinpref.pref

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.matsudamper.kotlinpref.KotlinPref
import net.matsudamper.kotlinpref.KotlinPrefModel
import kotlin.reflect.KProperty


@Suppress("NOTHING_TO_INLINE")
inline fun KotlinPrefModel.booleanPref(
    default: Boolean = false,
    key: String? = null
) = BooleanPref(default, key)

class BooleanPref(
    private val default: Boolean = false,
    private val key: String? = null
) : KotlinPref<Boolean>() {
    override fun write(property: KProperty<*>, value: Boolean, preference: SharedPreferences) {
        preference.edit {
            putBoolean(key ?: property.name, value)
        }
    }

    override fun read(property: KProperty<*>, preference: SharedPreferences): Boolean {
        return preference.getBoolean(key ?: property.name, default)
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun KotlinPrefModel.booleanPrefLiveData(
    default: Boolean = false,
    key: String
): LiveData<Boolean> {
    val liveData = MutableLiveData<Boolean>()
    functions[key] = {
        liveData.postValue(sharedPreferences.getBoolean(key, default))
    }.apply { invoke() }
    return liveData
}