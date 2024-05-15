package com.ucne.roomdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.ucne.myapplication.data.local.database.TicketDb
import com.ucne.myapplication.data.repository.TicketRepository
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
                NavHost(navController = navController, startDestination = Screen.TiketListScreen) {

                    composable<Screen.TiketListScreen> {
                        Text(text = "Estas en TicketList")
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
    object TiketListScreen : Screen()
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    RoomDemoTheme {

    }
}


