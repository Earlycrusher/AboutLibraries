package com.mikepenz.aboutlibraries.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MainLayout()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainLayout() {
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
    ) {
        var showAuthor by remember { mutableStateOf(true) }
        var showVersion by remember { mutableStateOf(true) }
        var showLicenseBadges by remember { mutableStateOf(true) }
        var showHeader by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                // We use TopAppBar from accompanist-insets-ui which allows us to provide
                // content padding matching the system bars insets.
                TopAppBar(
                    title = { Text("Compose Sample") },
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.9f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            WindowInsets.statusBars.asPaddingValues()
                        ),
                    actions = {
                        IconButton(onClick = {
                            showAuthor = !showAuthor
                        }) { Icon(Icons.Default.Person, "Author") }
                        IconButton(onClick = {
                            showVersion = !showVersion
                        }) { Icon(Icons.Default.Build, "Version") }
                        IconButton(onClick = { showLicenseBadges = !showLicenseBadges }) {
                            Icon(
                                Icons.AutoMirrored.Filled.List,
                                "Licenses"
                            )
                        }
                        IconButton(onClick = {
                            showHeader = !showHeader
                        }) { Icon(Icons.Default.Info, "Header") }
                    }
                )
            },
        ) { contentPadding ->
            LibrariesContainer(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = contentPadding.calculateTopPadding()),
                contentPadding = WindowInsets.navigationBars.asPaddingValues(),
                showAuthor = showAuthor,
                showVersion = showVersion,
                showLicenseBadges = showLicenseBadges,
                header = {
                    if (showHeader) {
                        stickyHeader {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colors.surface)
                                    .padding(vertical = 25.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text("ExampleHeader")
                            }
                        }
                    }
                }
            )
        }
    }
}