package com.ucne.roomdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.room.Room
import com.ucne.myapplication.data.local.database.TicketDb
import com.ucne.myapplication.data.local.entities.TicketEntity
import com.ucne.myapplication.presentation.TicketListScreen
import com.ucne.roomdemo.ui.theme.RoomDemoTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var ticketDb: TicketDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ticketDb = Room.databaseBuilder(
            this,
            TicketDb::class.java,
            "Ticket.db"
        )
            .fallbackToDestructiveMigration()
            .build()

        enableEdgeToEdge()
        setContent {
            RoomDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(8.dp)
                    ) {

                        val tickets: List<TicketEntity> by getTickets().collectAsStateWithLifecycle(
                            initialValue = emptyList()
                        )
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
                                            saveTicket(
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
                        Spacer(modifier = Modifier.padding(2.dp))


                        TicketListScreen(
                            tickets = tickets,
                            onVerTicket = { ticketSeleccionado ->
                                ticketId = ticketSeleccionado.ticketId.toString()
                                cliente = ticketSeleccionado.cliente
                                asunto = ticketSeleccionado.asunto
                            })
                    }
                }
            }
        }
    }

    fun saveTicket(ticket: TicketEntity) {
        GlobalScope.launch {
            ticketDb.ticketDao().save(ticket)
        }
    }

    fun getTickets(): Flow<List<TicketEntity>> {
        return ticketDb.ticketDao().getAll()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TicketScreen(
    cliente: String, asunto: String, onSaveTicket: () -> Unit
) {

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    RoomDemoTheme {

    }
}


