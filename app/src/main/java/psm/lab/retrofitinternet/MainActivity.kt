package psm.lab.retrofitinternet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import psm.lab.retrofitinternet.pages.Page1
import psm.lab.retrofitinternet.pages.Page2
import psm.lab.retrofitinternet.pages.Pages
import psm.lab.retrofitinternet.ui.theme.RetrofitInternetTheme
import retrofit2.Retrofit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            RetrofitInternetTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar(navController) }
    ) {
        NavHost(
            navController = navController,
            startDestination = Pages.Page1.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = Pages.Page1.name) { Page1() }
            composable(route = Pages.Page2.name) { Page2() }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
            Column() {
                Text(
                    text = "Page1",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )
                IconButton(onClick = { navController.navigate(route = Pages.Page1.name) }) {
                    Icon(Icons.TwoTone.Home, contentDescription = "Page1",
                        Modifier
                            .width(50.dp)
                            .height(50.dp))
                }
            }
            Column() {
                Text(
                    text = "Page2",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )
                IconButton(onClick = { navController.navigate(route = Pages.Page2.name) }) {
                    Icon(Icons.TwoTone.Star, contentDescription = "Page2",
                        Modifier
                            .width(50.dp)
                            .height(50.dp))
                }
            }
        }
    }
}

@Composable
fun TopBar() {
    Text(modifier = Modifier
             .padding(8.dp)
             .fillMaxWidth()
            .background(Color.Yellow),
        text = "Retrofit Example",
        fontSize = 30.sp,
        textAlign = TextAlign.Center)
}