package com.puremindit.ofa

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.puremindit.ofa.di.appModule
import com.puremindit.ofa.theme.AppTheme
import kotlinx.serialization.Serializable
import org.koin.compose.KoinApplication

@Serializable
object PostDetailDestination

@Composable
internal fun App() = KoinApplication({
    modules(appModule)
}) {
    AppTheme {
        val navController = rememberNavController()
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing),
            navController = navController,
            contentAlignment = Alignment.TopCenter,
            startDestination = PostDetailDestination
        ) {
            composable<PostDetailDestination> {
                PostDetailScreen() {}
            }

        }
    }
}
