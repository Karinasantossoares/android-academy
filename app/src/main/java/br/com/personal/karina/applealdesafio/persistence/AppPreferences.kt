package br.com.personal.karina.applealdesafio.persistence


import android.content.SharedPreferences

class AppPreferences(private val preferences: SharedPreferences) {

    fun saveStringKey(key: String, value: String) {
        val edit = preferences.edit()
        edit.putString(key, value)
        edit.apply()
    }

    fun getStringByKey(key: String) = preferences.getString(key, null)


    companion object {
        const val NAME = "AppDescription"
        const val EMAIL = "EMAIL"
        const val PASSWORD = "PASSWORD"
    }
}