package com.ucne.myapplication.data.remote

import com.ucne.myapplication.data.remote.dto.UsersDto
import retrofit2.http.GET

interface UsersApi {
    @GET("api/Usuarios")
   suspend fun getUsers(): List<UsersDto>

}

