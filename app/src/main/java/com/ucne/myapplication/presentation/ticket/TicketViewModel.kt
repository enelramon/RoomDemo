package com.ucne.myapplication.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.myapplication.data.local.entities.TicketEntity
import com.ucne.myapplication.data.remote.dto.UsersDto
import com.ucne.myapplication.data.repository.Resource
import com.ucne.myapplication.data.repository.TicketRepository
import com.ucne.myapplication.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val repository: TicketRepository,
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val ticketId: Int = 0
    var uiState = MutableStateFlow(TicketUIState())
        private set

    val tickets = repository.getTickets()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

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

            getUsers()
        }
    }

    fun onAsuntoChanged(asunto: String) {
        uiState.update {
            it.copy(asunto = asunto)
        }
    }

    fun onClienteChanged(cliente: String) {
        uiState.update {
            it.copy(cliente = cliente)
        }
    }


    fun saveTicket() {
        viewModelScope.launch {
            repository.saveTicket(uiState.value.toEntity())
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            usersRepository.getUsers().onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        uiState.update {
                            it.copy(isLoading = true)
                        }

                    }

                    is Resource.Success -> {
                        uiState.update {
                            it.copy(
                                isLoading = false,
                                users = result.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Error -> {
                        uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }
}

data class TicketUIState(
    val ticketId: Int = 0,
    var cliente: String = "",
    var clienteError: String? = null,
    var asunto: String = "",
    var asuntoError: String? = null,
    val users: List<UsersDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

fun TicketUIState.toEntity(): TicketEntity {
    return TicketEntity(
        ticketId = ticketId,
        cliente = cliente,
        asunto = asunto,
    )
}