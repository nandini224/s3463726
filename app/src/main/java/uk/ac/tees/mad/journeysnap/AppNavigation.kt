package uk.ac.tees.mad.journeysnap

import SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.journeysnap.ui.screen.auth.LoginScreen
import uk.ac.tees.mad.journeysnap.ui.screen.auth.SignupScreen
import uk.ac.tees.mad.journeysnap.ui.screen.auth.WelcomeScreen
import uk.ac.tees.mad.journeysnap.ui.screen.gallery.GalleryScreen
import uk.ac.tees.mad.journeysnap.utils.Constants.GALLERY_SCREEN
import uk.ac.tees.mad.journeysnap.utils.Constants.LOGIN_SCREEN
import uk.ac.tees.mad.journeysnap.utils.Constants.SIGNUP_SCREEN
import uk.ac.tees.mad.journeysnap.utils.Constants.SPLASH_SCREEN
import uk.ac.tees.mad.journeysnap.utils.Constants.WELCOME_SCREEN

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, SPLASH_SCREEN) {
        composable(SPLASH_SCREEN) {
            SplashScreen(navController)
        }

        composable(WELCOME_SCREEN) {
            WelcomeScreen(navController)
        }
        composable(LOGIN_SCREEN) {
            LoginScreen(navController)
        }

        composable(SIGNUP_SCREEN) {
            SignupScreen(navController)
        }

        composable(GALLERY_SCREEN) {
            GalleryScreen()
        }
    }
}