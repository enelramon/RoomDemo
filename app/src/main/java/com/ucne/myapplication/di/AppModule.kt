package com.ucne.myapplication.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ucne.myapplication.data.local.database.TicketDb
import com.ucne.myapplication.data.remote.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesTicketDatabase(@ApplicationContext appContext: Context): TicketDb =
        Room.databaseBuilder(
            appContext,
            TicketDb::class.java,
            "Ticket.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesTicketDao(db: TicketDb) = db.ticketDao()

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun providesTicketApi(moshi: Moshi): UsersApi {
        return Retrofit.Builder()
            .baseUrl("https://apirestpichardo.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(UsersApi::class.java)
    }
}