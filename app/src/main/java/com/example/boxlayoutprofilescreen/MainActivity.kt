package com.example.boxlayoutprofilescreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

// read ahead to ch 67

// light mode
val CafePalette = lightColorScheme(
    primary = Color(0xFF6F4E37),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDDBC7),
    onPrimaryContainer = Color(0xFF2D1600),
    secondaryContainer = Color(0xFFD2B48C),
    surface = Color(0xFFFFFBF8)
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(colorScheme = CafePalette) {
                ProfileScreen()
            }
        }
    }
}

// allows for using the TopAppBar if experimental (ch 66, we read later)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {

    // MAIN SCREEN WRAPPER
    Column(modifier = Modifier.fillMaxSize()) {
        // REQUIREMENT: M3 component - TopAppBar
        TopAppBar(title = { Text("VeryCoolCafe Rewards Profile") })

        // PROFILE HEADER SECTION
        // REQUIREMENT: Box for layering (Chapter 28.2)
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
        ) {

            // layer 1, the background header!
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(180.dp) // total height to accommodate the overlap)
                .background(MaterialTheme.colorScheme.primaryContainer)
            )

            // layer 2, the overlay card!
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    // align it to the bottom first
                    .align(Alignment.BottomCenter) // REQUIREMENT: align
                    // offset it to "pull" it up so it overlaps
                    .offset(y = (-10).dp) // REQUIREMENT: offset for overlap
                    .aspectRatio(1.8f), // REQUIREMENT: aspectRatio
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp) // REQUIREMENT: elevation
            ) {
                // actual content inside card
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom // push the text below the avatar area
                ) {
                    Text(
                        "Espresso Enthusiast",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    // REQUIREMENT: M3 component - Badge
                    Badge { Text("Active Now") }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        // M3 COMPONENT: FilledTonalButton
                        FilledTonalButton(onClick = {},
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )) { Text("My Rewards") }

                        Spacer(modifier = Modifier.width(8.dp))
                        // M3 COMPONENT: AssistChip
                        AssistChip(onClick = {}, label = { Text("Share") })
                    }
                }
            }

            // layer 3, the foreground avatar!
            // REQUIREMENT: M3 component - Surface
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = 70.dp) // adjusted slightly to bridge the background and card
                    .zIndex(1f) // REQUIREMENT: zIndex to ensure it's on top
                    .clip(CircleShape) // REQUIREMENT: clip(CircleShape)
                    .border(4.dp, MaterialTheme.colorScheme.surface, CircleShape),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                // REQUIREMENT: M3 component - Icon
                Icon(
                    // from https://developer.android.com/reference/kotlin/androidx/compose/material/icons/Icons#Default()
                    // and https://fonts.google.com/icons?icon.query=person
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.padding(24.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}