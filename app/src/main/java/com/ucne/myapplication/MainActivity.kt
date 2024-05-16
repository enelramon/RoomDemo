package com.ucne.roomdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import com.ucne.myapplication.data.local.database.TicketDb
import com.ucne.myapplication.data.repository.TicketRepository
import com.ucne.myapplication.presentation.ticket.TicketListScreen
import com.ucne.myapplication.presentation.ticket.TicketScreen
import com.ucne.myapplication.presentation.ticket.TicketViewModel
import com.ucne.roomdemo.ui.theme.RoomDemoTheme
import kotlinx.serialization.Serializable

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
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.TiketList) {

                    composable<Screen.TiketList> {
                        TicketListScreen(
                            viewModel = viewModel { TicketViewModel(repository,0) },
                            onVerTicket = {
                                navController.navigate(Screen.Ticket(it.ticketId ?: 0))
                            })
                    }

                    composable<Screen.Ticket> {
                        val args = it.toRoute<Screen.Ticket>()
                        TicketScreen(viewModel = viewModel { TicketViewModel(repository,args.ticketId) })
                    }
                }
                /* Surface {
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
                 }*/
            }
        }
    }
}

sealed class Screen {
    @Serializable
    object TiketList : Screen()

    @Serializable
    data class Ticket(val ticketId: Int) : Screen()
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    RoomDemoTheme {

    }
}


