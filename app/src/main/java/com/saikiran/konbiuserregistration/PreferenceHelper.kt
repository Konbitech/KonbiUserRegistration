package com.saikiran.konbiuserregistration

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object PreferenceHelper {
    const val CLOUD_URL = "CLOUD_URL"
    const val CLIENT_ID = "CLIENT_ID"
    const val CLIENT_SECRET = "CLIENT_SECRET"

    fun defaultPreference(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreference(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.cloudURL
        get() = getString(CLOUD_URL, "https://skyworks.vendingmachine.sg")
        set(value) {
            editMe {
                it.putString(CLOUD_URL, value)
            }
        }

    var SharedPreferences.clientID
        get() = getString(CLIENT_ID, "FrsC6oGdNAACxxzaJa0H72amxeZHnrE1pAPQCPF0")
        set(value) {
            editMe {
                it.putString(CLIENT_ID, value)
            }
        }
    var SharedPreferences.clientSecret
        get() = getString(CLIENT_SECRET, "9iiT7rs4QeBbLyFd8j6bLXfjLrXzIDYTw3QRuJzh")
        set(value) {
            editMe {
                it.putString(CLIENT_SECRET, value)
            }
        }

    var SharedPreferences.clearValues
        get() = { }
        set(value) {
            editMe {
                it.clear()
            }
        }
}