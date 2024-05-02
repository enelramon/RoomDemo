package com.ucne.myapplication.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//data/local/entities
@Entity(tableName = "Tickets")
data class TicketEntity(
    @PrimaryKey
    val ticketId: Int? = null,
    var cliente: String = "",
    var asunto: String = ""
)