package com.muhammad.reeltime.auth.data.remote.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.muhammad.reeltime.auth.data.remote.dto.AuthRequest
import com.muhammad.reeltime.auth.data.remote.dto.AuthResponse
import com.muhammad.reeltime.auth.domain.repository.AuthRepository
import com.muhammad.reeltime.utils.BackendConstants
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.EmptyResult
import com.muhammad.reeltime.utils.Result
import com.muhammad.reeltime.utils.asEmptyDataResult
import com.muhammad.reeltime.utils.post
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers

class AuthRespositoryImp(
    private val httpClient: HttpClient,
    private val prefs: SharedPreferences,
) : AuthRepository {
    override suspend fun register(
        request: AuthRequest,
    ): EmptyResult<DataError.Network> {
        return httpClient.post<AuthRequest, Unit>(
            route = "${BackendConstants.BACKEND_BASE_URL}${BackendConstants.REGISTER}",
            body = request
        )
    }

    override suspend fun login(
        request: AuthRequest,
    ): EmptyResult<DataError.Network> {
        val result = httpClient.post<AuthRequest, AuthResponse>(
            route = "${BackendConstants.BACKEND_BASE_URL}${BackendConstants.LOGIN}", body = request,
        )
        if (result is Result.Success) {
            val data = result.data
            prefs.edit { putString("email", request.email) }
            prefs.edit { putString("name", data.name) }
            prefs.edit { putString("token", data.token) }
        }
        return result.asEmptyDataResult()
    }

    override suspend fun authenticate() {
        httpClient.get(BackendConstants.AUTHENTICATE) {
            headers {
                append("Authorization", prefs.getString("token", null).toString())
            }
        }
    }

    override fun logout() {
        prefs.edit { putString("email", null) }
        prefs.edit { putString("name", null) }
        prefs.edit { putString("token", null) }
    }
}