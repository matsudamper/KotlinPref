package net.matsudamper.kotlinpref

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class KotlinPref<T : Any> : ReadWriteProperty<KotlinPrefModel, T> {

    override fun getValue(thisRef: KotlinPrefModel, property: KProperty<*>): T {
        return read(property, thisRef.sharedPreferences)
    }

    override fun setValue(thisRef: KotlinPrefModel, property: KProperty<*>, value: T) {
        write(property, value, thisRef.sharedPreferences)
    }

    abstract fun write(property: KProperty<*>, value: T, preference: SharedPreferences)
    abstract fun read(property: KProperty<*>, preference: SharedPreferences): T
}