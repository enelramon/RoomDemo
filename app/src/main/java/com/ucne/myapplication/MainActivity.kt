package com.ucne.roomdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ucne.myapplication.presentation.ticket.TicketListScreen
import com.ucne.myapplication.presentation.ticket.TicketScreen
import com.ucne.roomdemo.ui.theme.RoomDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RoomDemoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.TiketList) {

                    composable<Screen.TiketList> {
                        TicketListScreen(
                            onVerTicket = {
                                navController.navigate(Screen.Ticket(it.ticketId ?: 0))
                            })
                    }

                    composable<Screen.Ticket> {
                        val args = it.toRoute<Screen.Ticket>()
                        TicketScreen()
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


