package com.ucne.myapplication.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ucne.myapplication.data.local.dao.TicketDao
import com.ucne.myapplication.data.local.entities.TicketEntity

//data/local/database
@Database(
    entities = [
        TicketEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TicketDb : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}