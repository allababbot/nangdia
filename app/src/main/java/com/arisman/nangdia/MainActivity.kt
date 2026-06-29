package com.arisman.nangdia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arisman.nangdia.presentation.lists.ListsScreen
import com.arisman.nangdia.presentation.search.SearchScreen
import com.arisman.nangdia.presentation.search.AdvancedSearchScreen
import com.arisman.nangdia.presentation.home.HomeScreen
import com.arisman.nangdia.presentation.watchlist.WatchlistScreen
import com.arisman.nangdia.presentation.calendar.CalendarScreen
import com.arisman.nangdia.presentation.detail.MediaDetailScreen
import com.arisman.nangdia.presentation.detail.PersonDetailScreen
import com.arisman.nangdia.presentation.detail.MediaSeasonsScreen
import com.arisman.nangdia.presentation.awards.AwardsScreen
import com.arisman.nangdia.presentation.settings.SettingsScreen
import com.arisman.nangdia.domain.model.MediaSearchResult
import com.arisman.nangdia.ui.theme.PilemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PilemTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = {
                        val showBottomBar = currentDestination?.route in listOf("home", "search", "watchlist", "calendar", "lists", "awards")
                        if (showBottomBar) {
                            NavigationBar(
                                modifier = Modifier.height(48.dp),
                                containerColor = MaterialTheme.colorScheme.surface
                            ) {
                                NavigationBarItem(
                                    selected = currentDestination?.hierarchy?.any { it.route == "home" } == true,
                                    onClick = { navController.navigate("home") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } },
                                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                                    label = null,
                                    alwaysShowLabel = false,
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                        indicatorColor = MaterialTheme.colorScheme.primary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                                NavigationBarItem(
                                    selected = currentDestination?.hierarchy?.any { it.route == "search" } == true,
                                    onClick = { navController.navigate("search") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } },
                                    icon = { Icon(Icons.Default.Search, contentDescription = null) },
                                    label = null,
                                    alwaysShowLabel = false,
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                        indicatorColor = MaterialTheme.colorScheme.primary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                                NavigationBarItem(
                                    selected = currentDestination?.hierarchy?.any { it.route == "lists" } == true,
                                    onClick = { navController.navigate("lists") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } },
                                    icon = { Icon(Icons.Default.Explore, contentDescription = null) },
                                    label = null,
                                    alwaysShowLabel = false,
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                        indicatorColor = MaterialTheme.colorScheme.primary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                                NavigationBarItem(
                                    selected = currentDestination?.hierarchy?.any { it.route == "calendar" } == true,
                                    onClick = { navController.navigate("calendar") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } },
                                    icon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) },
                                    label = null,
                                    alwaysShowLabel = false,
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                        indicatorColor = MaterialTheme.colorScheme.primary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                                NavigationBarItem(
                                    selected = currentDestination?.hierarchy?.any { it.route == "watchlist" } == true,
                                    onClick = { navController.navigate("watchlist") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } },
                                    icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                                    label = null,
                                    alwaysShowLabel = false,
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                        indicatorColor = MaterialTheme.colorScheme.primary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                                NavigationBarItem(
                                    selected = currentDestination?.hierarchy?.any { it.route == "awards" } == true,
                                    onClick = { navController.navigate("awards") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } },
                                    icon = { Icon(Icons.Default.EmojiEvents, contentDescription = null) },
                                    label = null,
                                    alwaysShowLabel = false,
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                        indicatorColor = MaterialTheme.colorScheme.primary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = innerPadding.calculateBottomPadding()),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "home",
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300)) + fadeIn(tween(300)) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300)) + fadeOut(tween(300)) },
                            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300)) + fadeIn(tween(300)) },
                            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300)) + fadeOut(tween(300)) }
                        ) {
                            composable("home") {
                                HomeScreen(
                                    onNavigateToDetail = { media ->
                                        val mediaId = media.imdbId ?: media.tmdbId?.toString() ?: media.id.toString()
                                        val provider = if (media.imdbId != null) "imdb" else "tmdb"
                                        navController.navigate("detail/$mediaId/${media.mediaType}/$provider")
                                    }
                                )
                            }
                            composable("lists") {
                                ListsScreen(
                                    onNavigateToDetail = { media ->
                                        val mediaId = media.imdbId ?: media.tmdbId?.toString() ?: media.traktId?.toString() ?: media.id.toString()
                                        val provider = when {
                                            media.imdbId != null -> "imdb"
                                            media.traktId != null -> "trakt"
                                            else -> "tmdb"
                                        }
                                        navController.navigate("detail/$mediaId/${media.mediaType}/$provider")
                                    }
                                )
                            }
                            composable("search") {
                                SearchScreen(
                                    onNavigateToDetail = { media ->
                                        val mediaId = media.imdbId ?: media.tmdbId?.toString() ?: media.traktId?.toString() ?: media.id.toString()
                                        val provider = when {
                                            media.imdbId != null -> "imdb"
                                            media.traktId != null -> "trakt"
                                            else -> "tmdb"
                                        }
                                        navController.navigate("detail/$mediaId/${media.mediaType}/$provider")
                                    },
                                    onNavigateToAdvancedSearch = {
                                        navController.navigate("advanced_search")
                                    }
                                )
                            }
                            composable("advanced_search") {
                                AdvancedSearchScreen(
                                    onNavigateBack = { navController.popBackStack() },
                                    onNavigateToDetail = { media ->
                                        val mediaId = media.imdbId ?: media.tmdbId?.toString() ?: media.id.toString()
                                        val provider = if (media.imdbId != null) "imdb" else "tmdb"
                                        navController.navigate("detail/$mediaId/${media.mediaType}/$provider")
                                    }
                                )
                            }
                            composable("watchlist") {
                                WatchlistScreen(
                                    onNavigateToDetail = { mediaId, mediaType, provider ->
                                        navController.navigate("detail/$mediaId/$mediaType/$provider")
                                    }
                                )
                            }
                            composable("calendar") {
                                CalendarScreen(
                                    onNavigateBack = { navController.popBackStack() },
                                    onNavigateToDetail = { media ->
                                        val mediaId = media.imdbId ?: media.tmdbId?.toString() ?: media.id.toString()
                                        val provider = if (media.imdbId != null) "imdb" else "tmdb"
                                        navController.navigate("detail/$mediaId/${media.mediaType}/$provider")
                                    }
                                )
                            }
                            composable(
                                route = "detail/{mediaId}/{mediaType}/{provider}",
                                arguments = listOf(
                                    navArgument("mediaId") { type = NavType.StringType },
                                    navArgument("mediaType") { type = NavType.StringType },
                                    navArgument("provider") { type = NavType.StringType }
                                )
                            ) {
                                MediaDetailScreen(
                                    onNavigateToDetail = { id, type, provider ->
                                        navController.navigate("detail/$id/$type/$provider")
                                    },
                                    onNavigateToPerson = { personId ->
                                        navController.navigate("person/$personId")
                                    },
                                    onNavigateToSeasons = { id, type, season ->
                                        navController.navigate("media_seasons/$id/$type/$season")
                                    },
                                    onNavigateBack = { navController.popBackStack() }
                                )
                            }
                            composable(
                                route = "person/{personId}",
                                arguments = listOf(
                                    navArgument("personId") { type = NavType.IntType }
                                )
                            ) {
                                PersonDetailScreen(
                                    onNavigateToDetail = { id, type, provider ->
                                        navController.navigate("detail/$id/$type/$provider")
                                    },
                                    onNavigateBack = { navController.popBackStack() }
                                )
                            }
                            composable(
                                route = "media_seasons/{mediaId}/{mediaType}/{provider}",
                                arguments = listOf(
                                    navArgument("mediaId") { type = NavType.StringType },
                                    navArgument("mediaType") { type = NavType.StringType },
                                    navArgument("provider") { type = NavType.StringType }
                                )
                            ) {
                                MediaSeasonsScreen(
                                    onNavigateBack = { navController.popBackStack() }
                                )
                            }
                            composable("awards") {
                                AwardsScreen(
                                    onNavigateBack = { navController.popBackStack() }
                                )
                            }
                            composable("settings") {
                                SettingsScreen(
                                    onNavigateBack = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
