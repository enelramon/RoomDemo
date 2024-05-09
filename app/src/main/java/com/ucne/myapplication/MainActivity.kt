package com.ucne.roomdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.ucne.myapplication.data.local.database.TicketDb
import com.ucne.myapplication.data.repository.TicketRepository
import com.ucne.myapplication.presentation.ticket.TicketListScreen
import com.ucne.myapplication.presentation.ticket.TicketScreen
import com.ucne.myapplication.presentation.ticket.TicketViewModel
import com.ucne.roomdemo.ui.theme.RoomDemoTheme

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

        val repository = TicketRepository(ticketDb.ticketDao())
        enableEdgeToEdge()
        setContent {
            RoomDemoTheme {
                Surface {
                    val viewModel: TicketViewModel = viewModel(
                        factory = TicketViewModel.provideFactory(repository)
                    )
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .padding(8.dp)
                        ) {

                            TicketScreen(viewModel = viewModel)
                            TicketListScreen(viewModel = viewModel,
                                onVerTicket = {

                                })
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    RoomDemoTheme {

    }
}


