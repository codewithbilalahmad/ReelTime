package com.muhammad.reeltime.auth.domain.repository

import com.muhammad.reeltime.auth.data.remote.dto.AuthRequest
import com.muhammad.reeltime.utils.DataError
import com.muhammad.reeltime.utils.EmptyResult
import com.muhammad.reeltime.utils.Result

interface AuthRepository {
    suspend fun register(
        request: AuthRequest,
    ): EmptyResult<DataError.Network>

    suspend fun login(
        request: AuthRequest,
    ): EmptyResult<DataError.Network>

    suspend fun authenticate()
    fun logout()
}