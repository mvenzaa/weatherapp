package com.venza.wheaterapp.core.utils

import com.venza.wheaterapp.core.utils.AppConstants.IS_FIRST
import com.venza.wheaterapp.core.utils.AppConstants.LOCALE
import java.util.*

object ExtensionsApp {
    fun getLocale(appPreferences: AppPreferences): String =appPreferences.getStringValue(LOCALE, Locale.getDefault().language)
    fun isFirst(appPreferences: AppPreferences): Boolean =appPreferences.getBooleanValue(IS_FIRST , false)
}