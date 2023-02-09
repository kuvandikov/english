package com.kuvandikov.english.data.local.preferences

import android.content.Context
import android.content.SharedPreferences


const val APP_ID = "application_id"
const val HOME_UNIT_ID = "home_unit_id"
const val SAVED_UNIT_ID = "saved_unit_id"
const val SETTINGS_UNIT_ID = "settings_unit_id"

class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("english.preferences", Context.MODE_PRIVATE)

    private fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun setAppId(appId: String) = preferences.edit().putString(APP_ID,appId).apply()
    fun getAppId() = preferences.getString(APP_ID,null)

    fun setHomeUnitId(unitId: String) = preferences.edit().putString(HOME_UNIT_ID,unitId).apply()
    fun getHomeUnitId() = preferences.getString(HOME_UNIT_ID,null)

    fun setSavedUnitId(unitId: String) = preferences.edit().putString(SAVED_UNIT_ID,unitId).apply()
    fun getSavedUnitId() = preferences.getString(SAVED_UNIT_ID,null)

    fun setSettingsUnitId(unitId: String) = preferences.edit().putString(SETTINGS_UNIT_ID,unitId).apply()
    fun getSettingsUnitId() = preferences.getString(SETTINGS_UNIT_ID,null)



}