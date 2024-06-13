package com.ucne.myapplication.data.repository

import com.ucne.myapplication.data.remote.UsersApi
import com.ucne.myapplication.data.remote.dto.UsersDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val usersApi: UsersApi
) {
    suspend fun getUsers(): Flow<Resource<List<UsersDto>>> = flow {
        emit(Resource.Loading())
        try {
            val users = usersApi.getUsers()
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}