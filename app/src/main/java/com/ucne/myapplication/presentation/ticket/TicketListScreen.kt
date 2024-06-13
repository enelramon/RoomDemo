package com.ucne.myapplication.presentation.ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.myapplication.data.local.entities.TicketEntity
import com.ucne.myapplication.presentation.components.TopAppBar
import com.ucne.roomdemo.ui.theme.RoomDemoTheme

@Composable
fun TicketListScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    onVerTicket: (TicketEntity) -> Unit
) {
    val tickets by viewModel.tickets.collectAsStateWithLifecycle()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        TextButton(
            onClick = { viewModel.getUsers() }
        ) {
            Text(text = "Get Users")
        }

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

     uiState.errorMessage?.let {
         Text(text = it, color = Color.Red)
     }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.users) { user ->
                Text(text = user.usuarioId.toString())
                Text(text = user.nombre)
                Text(text = user.apellido)
            }
        }
    }
    /* TicketListBody(
         tickets = tickets,
         onVerTicket = onVerTicket
     )*/
}

@Composable
fun TicketListBody(
    tickets: List<TicketEntity>,
    onVerTicket: (TicketEntity) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = "Tickets") }) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(tickets) { ticket ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onVerTicket(ticket) }
                            .padding(16.dp)
                    ) {
                        Text(text = ticket.ticketId.toString(), modifier = Modifier.weight(0.10f))
                        Text(text = ticket.cliente, modifier = Modifier.weight(0.400f))
                        Text(text = ticket.asunto, modifier = Modifier.weight(0.40f))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TicketListPreview() {
    val tickets = listOf(
        TicketEntity(
            cliente = "Enel Almonte",
            asunto = "Ayuda impresora"
        )
    )
    RoomDemoTheme {
        TicketListBody(tickets = tickets) {

        }
    }
}

