package com.example.musicstreamingapp.Presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicstreamingapp.Presentation.data.NavItem
import com.example.musicstreamingapp.Presentation.screen.HomePage
import com.example.musicstreamingapp.Presentation.screen.SearchScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    var selected by remember {
        mutableIntStateOf(0)
    }

    val list = listOf(
        NavItem("Home", Icons.Default.Home, "home"),
        NavItem("Library", Icons.Default.List, "library")
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerState = drawerState, gesturesEnabled = false,

        drawerContent = {
            ModalDrawerSheet {
                Text("Ok Music", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(label = { Text(text = "About") },
                    selected = false,
                    onClick = { })
                NavigationDrawerItem(label = { Text(text = "Subscribe") },
                    selected = false,
                    onClick = { })
                NavigationDrawerItem(label = { Text(text = "Sign Out") },
                    selected = false,
                    onClick = { })
                NavigationDrawerItem(label = { Text(text = "Go Back") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isOpen) close() else open()
                            }
                        }
                    })
            }
        }) {
        Scaffold(topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "List",
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }

                },
                actions ={

                },
                title = { Text(text = "Ok Music") },
            )
        }, bottomBar = {
            NavigationBar {
                list.forEachIndexed() { index, navItem ->
                    NavigationBarItem(selected = selected == index,
                        onClick = { selected = index },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = null)
                        },
                        label = { Text(text = navItem.title) })

                }

            }
        }) { innerPadding ->
            Content(selectedIndex = selected, modifier = Modifier.padding(innerPadding))
        }
    }

}


@Composable
fun Content(selectedIndex: Int, modifier: Modifier) {
    when (selectedIndex) {
        0 -> HomePage()
        1 -> SearchScreen()
    }
}