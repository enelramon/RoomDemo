package com.ucne.myapplication.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.myapplication.data.local.entities.TicketEntity
import com.ucne.myapplication.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TicketViewModel(private val repository: TicketRepository, private val ticketId: Int) :
    ViewModel() {

    var uiState = MutableStateFlow(TicketUIState())
        private set

    val tickets = repository.getTickets()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onAsuntoChanged(asunto: String){
        uiState.update {
            it.copy(asunto = asunto)
        }
    }
    fun onClienteChanged(cliente: String){
        uiState.update {
            it.copy(cliente = cliente)
        }
    }
    init {
        viewModelScope.launch {
            val ticket = repository.getTicket(ticketId)

            ticket?.let {
                uiState.update {
                    it.copy(
                        ticketId = ticket.ticketId ?: 0,
                        cliente = ticket.cliente,
                        asunto = ticket.asunto
                    )
                }
            }
        }
    }

    fun saveTicket() {
        viewModelScope.launch {
            repository.saveTicket(uiState.value.toEntity())
        }
    }
}

data class TicketUIState(
    val ticketId: Int = 0,
    var cliente: String = "",
    var clienteError: String? = null,
    var asunto: String = "",
    var asuntoError: String? = null,
)

fun TicketUIState.toEntity(): TicketEntity {
    return TicketEntity(
        ticketId = ticketId,
        cliente = cliente,
        asunto = asunto,
    )
}