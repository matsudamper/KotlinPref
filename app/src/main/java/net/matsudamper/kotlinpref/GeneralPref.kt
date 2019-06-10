package net.matsudamper.kotlinpref

import android.content.Context
import net.matsudamper.kotlinpref.pref.*


class GeneralPref(context: Context) : KotlinPrefModel(context) {

    // Enum
    enum class ColorState {
        YELLOW, ORANGE, RED, BLUE
    }

    var colorState: ColorState by enumPref(ColorState.YELLOW)
    val repeatStateLiveData by lazy {
        enumPrefLiveData(default = ColorState.YELLOW, key = GeneralPref::colorState.name)
    }

    // Bool
    var isTrue by booleanPref()
    val isTrueLiveData by lazy {
        booleanPrefLiveData(key = GeneralPref::isTrue.name)
    }

    // String
    var stringData: String by stringPref()
}
