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

data class CampusPlace(
    val title: String,
    val details: String,
    val routeSteps: List<String>
)

sealed class AppPage {
    object Home : AppPage()
    object Features : AppPage()
    object About : AppPage()
    data class PlaceInfo(val place: CampusPlace) : AppPage()
}

val campusPlaces = listOf(
    CampusPlace(
        title = "Library",
        details = "A quiet learning space with books, computers, and areas for private study.",
        routeSteps = listOf("Start at Main Entrance", "Walk to Student Life Building", "Continue to Library")
    ),
    CampusPlace(
        title = "Lecture Hall",
        details = "A large academic space used for lectures, teaching sessions, and seminars.",
        routeSteps = listOf("Start at Main Entrance", "Go towards Teaching Block", "Enter Lecture Hall")
    ),
    CampusPlace(
        title = "Cafeteria",
        details = "A campus food area where students and staff can get meals, snacks, and drinks.",
        routeSteps = listOf("Start at Main Entrance", "Move towards Campus Centre", "Arrive at Cafeteria")
    ),
    CampusPlace(
        title = "Student Support",
        details = "A help desk for student guidance, wellbeing support, and academic advice.",
        routeSteps = listOf("Start at Main Entrance", "Go to Student Life Building", "Find Student Support Desk")
    ),
    CampusPlace(
        title = "Computer Lab",
        details = "A practical computer room for coursework, printing, and digital learning tasks.",
        routeSteps = listOf("Start at Main Entrance", "Walk to Computing Building", "Enter Lab 2")
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
    var currentPage by remember { mutableStateOf<AppPage>(AppPage.Home) }

    when (val page = currentPage) {
        is AppPage.Home -> HomeScreen(
            places = campusPlaces,
            onPlaceClick = { selectedPlace ->
                currentPage = AppPage.PlaceInfo(selectedPlace)
            },
            onFeaturesClick = {
                currentPage = AppPage.Features
            },
            onAboutClick = {
                currentPage = AppPage.About
            }
        )

        is AppPage.PlaceInfo -> PlaceInfoScreen(
            place = page.place,
            onBackClick = {
                currentPage = AppPage.Home
            }
        )

        is AppPage.Features -> FeaturesScreen(
            onBackClick = {
                currentPage = AppPage.Home
            }
        )

        is AppPage.About -> AboutScreen(
            onBackClick = {
                currentPage = AppPage.Home
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    places: List<CampusPlace>,
    onPlaceClick: (CampusPlace) -> Unit,
    onFeaturesClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("TeesNav") })
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
                text = "Choose a destination to view details and step-by-step route guidance.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(places) { place ->
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPlaceClick(place) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = place.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = place.details,
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
                Text("View Features")
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
fun PlaceInfoScreen(
    place: CampusPlace,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(place.title) },
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
                text = place.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = place.details,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Step-by-Step Route",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    place.routeSteps.forEachIndexed { index, step ->
                        Text(
                            text = "${index + 1}. $step",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
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
                text = "Current Features",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("• Browse campus destinations")
            Text("• View location information")
            Text("• Follow step-by-step route guidance")
            Text("• Move between app sections")
            Text("• Simple mobile-friendly design")

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Future Improvements",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("• Interactive campus map")
            Text("• Live route updates")
            Text("• Accessibility-friendly routes")
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
                title = { Text("About TeesNav") },
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
                text = "TeesNav is a smart campus navigation prototype designed to help students, staff, and visitors find important campus locations more easily.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "This version demonstrates destination selection, location details, and step-by-step route guidance for a mobile campus navigation app.",
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
fun PreviewTeesNavApp() {
    MaterialTheme {
        TeesNavApp()
    }
}
