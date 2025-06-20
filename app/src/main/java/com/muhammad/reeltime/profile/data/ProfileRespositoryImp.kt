package com.muhammad.reeltime.profile.data

import android.content.SharedPreferences
import com.muhammad.reeltime.main.ReelTimeApplication
import com.muhammad.reeltime.profile.domain.ProfileRepository
import com.muhammad.reeltime.R

class ProfileRespositoryImp(
    private val prefs: SharedPreferences,
) : ProfileRepository {
    private val context = ReelTimeApplication.INSTANCE
    override suspend fun getName(): String {
        return prefs.getString("name", null) ?: context.getString(R.string.name)
    }

    override suspend fun getEmail(): String {
        return prefs.getString("email", null) ?: context.getString(R.string.email)
    }
}