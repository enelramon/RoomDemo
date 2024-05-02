package com.ucne.myapplication.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ucne.myapplication.data.local.entities.TicketEntity

@Composable
fun TicketListScreen(
    tickets: List<TicketEntity>,
    onVerTicket: (TicketEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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


