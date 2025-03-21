package uk.ac.tees.mad.journeysnap

import SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.journeysnap.utils.Constants.SPLASH_SCREEN

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, SPLASH_SCREEN) {
        composable(SPLASH_SCREEN) {
            SplashScreen()
        }
    }
}