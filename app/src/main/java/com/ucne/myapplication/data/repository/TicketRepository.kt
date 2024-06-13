package com.ucne.myapplication.data.repository

import com.ucne.myapplication.data.local.dao.TicketDao
import com.ucne.myapplication.data.local.entities.TicketEntity
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketDao: TicketDao
) {
    suspend fun saveTicket(ticket: TicketEntity) = ticketDao.save(ticket)

    fun getTickets() = ticketDao.getAll()

    suspend fun getTicket(ticketId: Int) = ticketDao.find(ticketId)
}

