package net.matsudamper.kotlinpref

import android.content.Context
import net.matsudamper.kotlinpref.pref.stringPref

class UserPref(context: Context, userId: Long) : KotlinPrefModel(context, userId.toString()) {
    var userText by stringPref()
}