package com.ucne.myapplication.presentation.ticket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.myapplication.data.local.entities.TicketEntity
import com.ucne.roomdemo.ui.theme.RoomDemoTheme

@Composable
fun TicketScreen(
    viewModel: TicketViewModel
) {
    val tickets by viewModel.tickets.collectAsStateWithLifecycle()
    TicketBody(
        onSaveTicket = { ticket ->
            viewModel.saveTicket(ticket)
        }
    )
}

@Composable
fun TicketBody(onSaveTicket: (TicketEntity) -> Unit) {
    var ticketId by remember { mutableStateOf("") }
    var cliente by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {


            OutlinedTextField(
                label = { Text(text = "Cliente") },
                value = cliente,
                onValueChange = { cliente = it },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = { Text(text = "Asunto") },
                value = asunto,
                onValueChange = { asunto = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = {
                        ticketId = ""
                        cliente = ""
                        asunto = ""
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "new button"
                    )
                    Text(text = "Nuevo")
                }
                OutlinedButton(
                    onClick = {
                        onSaveTicket(
                            TicketEntity(
                                ticketId = ticketId.toIntOrNull(),
                                cliente = cliente,
                                asunto = asunto
                            )
                        )
                        ticketId = ""
                        cliente = ""
                        asunto = ""
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "save button"
                    )
                    Text(text = "Guardar")
                }
            }
        }

    }

}


@Preview
@Composable
private fun TicketPreview() {
    RoomDemoTheme {
        TicketBody() {
        }
    }
}