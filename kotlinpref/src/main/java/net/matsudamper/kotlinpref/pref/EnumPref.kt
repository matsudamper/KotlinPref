package net.matsudamper.kotlinpref.pref

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.matsudamper.kotlinpref.KotlinPref
import net.matsudamper.kotlinpref.KotlinPrefModel
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


@Suppress("NOTHING_TO_INLINE")
inline fun <reified T : Enum<*>> KotlinPrefModel.enumPref(
        default: T,
        key: String? = null
) = EnumPref(T::class, default, key)

class EnumPref<T : Enum<*>>(
        enumClass: KClass<T>,
        private val default: T,
        private val key: String? = null
) : KotlinPref<T>() {
    private val enumConstants = enumClass.java.enumConstants!!
    override fun write(property: KProperty<*>, value: T, preference: SharedPreferences) {
        preference.edit {
            putString(key ?: property.name, value.name)
        }
    }

    override fun read(property: KProperty<*>, preference: SharedPreferences): T {
        val value = preference.getString(key ?: property.name, default.name)
        return enumConstants.first { it.name == value }
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun <reified T : Enum<*>> KotlinPrefModel.enumPrefLiveData(
        default: T,
        key: String
): LiveData<T> {
    val liveData = MutableLiveData<T>()
    functions[key] = {
        liveData.postValue(T::class.java.enumConstants!!.first { it.name == sharedPreferences.getString(key, default.name) })
    }.apply { invoke() }
    return liveData
}