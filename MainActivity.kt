package com.example.teesanav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class CampusLocation(
    val name: String,
    val description: String,
    val route: String
)

val locations = listOf(
    CampusLocation(
        "Library",
        "Main study area with books, computers and quiet study spaces.",
        "Route: Main entrance → Student Life Building → Library"
    ),
    CampusLocation(
        "Lecture Hall",
        "Large teaching space used for lectures and seminars.",
        "Route: Main entrance → Teaching Block → Lecture Hall"
    ),
    CampusLocation(
        "Cafeteria",
        "Food and drinks available throughout the day.",
        "Route: Main entrance → Campus Centre → Cafeteria"
    ),
    CampusLocation(
        "Student Support",
        "Support desk for student advice, wellbeing and academic guidance.",
        "Route: Main entrance → Student Life Building → Student Support Desk"
    ),
    CampusLocation(
        "Computer Lab",
        "Computer room for coursework, printing and practical sessions.",
        "Route: Main entrance → Computing Building → Lab 2"
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TeesNavApp()
            }
        }
    }
}

@Composable
fun TeesNavApp() {
    var screen by remember { mutableStateOf("home") }
    var selectedLocation by remember { mutableStateOf<CampusLocation?>(null) }

    when (screen) {
        "home" -> HomeScreen(
            onLocationClick = { location ->
                selectedLocation = location
                screen = "detail"
            },
            onAboutClick = { screen = "about" },
            onFeaturesClick = { screen = "features" }
        )

        "detail" -> {
            val location = selectedLocation
            if (location != null) {
                DetailScreen(
                    location = location,
                    onBackClick = {
                        selectedLocation = null
                        screen = "home"
                    }
                )
            } else {
                screen = "home"
            }
        }

        "about" -> AboutScreen(
            onBackClick = { screen = "home" }
        )

        "features" -> FeaturesScreen(
            onBackClick = { screen = "home" }
        )

        else -> {
            screen = "home"
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLocationClick: (CampusLocation) -> Unit,
    onAboutClick: () -> Unit,
    onFeaturesClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TeesNav") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Text(
                text = "Smart Campus Navigator",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Select a campus destination to view information and a suggested route.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(locations) { location ->
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLocationClick(location) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = location.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = location.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            Button(
                onClick = onFeaturesClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("App Features")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onAboutClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("About TeesNav")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    location: CampusLocation,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(location.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Text(
                text = location.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = location.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Suggested Route",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = location.route,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Home")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturesScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Features") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Current Prototype Features",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("• View key campus locations")
            Text("• Read location descriptions")
            Text("• See suggested indoor routes")
            Text("• Navigate between screens")
            Text("• Simple mobile-friendly interface")

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Future Improvements",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("• Interactive map")
            Text("• Real-time route updates")
            Text("• Accessibility-friendly route options")
            Text("• Reinforcement learning route optimisation")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Home")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Text(
                text = "About TeesNav",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "TeesNav is a smart campus navigation prototype designed to help students, staff and visitors find important campus locations more easily.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "This prototype demonstrates basic navigation, destination selection and route suggestion features for a mobile campus navigation app.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Home")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    MaterialTheme {
        TeesNavApp()
    }
}