package com.ucne.myapplication.data.repository

import com.ucne.myapplication.data.local.dao.TicketDao
import com.ucne.myapplication.data.local.entities.TicketEntity

class TicketRepository(private val ticketDao: TicketDao) {
    suspend fun saveTicket(ticket: TicketEntity) = ticketDao.save(ticket)

    fun getTickets() = ticketDao.getAll()
}