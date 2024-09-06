package com.potatomeme.screen.planfit.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.potatomeme.screen.planfit.data.model.PlanfitLoginType
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "planfit_settings")

class PlanfitPreferencesDataStore @Inject constructor(
    private val context: Context,
) {

    private object PreferensKeys {
        val LOGIN_TYPE = intPreferencesKey("login_type")
    }

    suspend fun getPlanfitLoginType(): PlanfitLoginType {
        val value = context.preferencesDataStore.data.map {
                it[PreferensKeys.LOGIN_TYPE]
            }.firstOrNull() ?: 0

        return PlanfitLoginType.fromType(value)
    }

    suspend fun setPlanfitLoginType(type: PlanfitLoginType) {
        context.preferencesDataStore.edit {
            it[PreferensKeys.LOGIN_TYPE] = type.num
        }
    }
}


