import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import uk.ac.tees.mad.journeysnap.R
import uk.ac.tees.mad.journeysnap.utils.Constants.GALLERY_SCREEN
import uk.ac.tees.mad.journeysnap.utils.Constants.SPLASH_SCREEN
import uk.ac.tees.mad.journeysnap.utils.Constants.WELCOME_SCREEN

@Composable
fun SplashScreen(navController: NavController) {

    val backgroundColor = Color(0xFFAA60C8)

    var showLogo by remember { mutableStateOf(false) }
    var showCircle by remember { mutableStateOf(false) }
    var showAppName by remember { mutableStateOf(false) }
    var showTagline by remember { mutableStateOf(false) }

    val currentUser = FirebaseAuth.getInstance().currentUser

    val logoScale = animateFloatAsState(
        targetValue = if (showLogo) 1f else 0.5f,
        animationSpec = tween(1000)
    )

    val logoAlpha = animateFloatAsState(
        targetValue = if (showLogo) 1f else 0f,
        animationSpec = tween(1500)
    )

    val circleScale = animateFloatAsState(
        targetValue = if (showCircle) 1f else 0.85f,
        animationSpec = tween(1000)
    )

    val circleAlpha = animateFloatAsState(
        targetValue = if (showCircle) 0.8f else 0f,
        animationSpec = tween(1000)
    )

    val infiniteTransition = rememberInfiniteTransition()

    val circle1Radius = infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val circle2Radius = infiniteTransition.animateFloat(
        initialValue = 15f,
        targetValue = 25f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val circle3Radius = infiniteTransition.animateFloat(
        initialValue = 25f,
        targetValue = 35f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val circle4Radius = infiniteTransition.animateFloat(
        initialValue = 18f,
        targetValue = 28f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(key1 = true) {
        showLogo = true
        delay(500)
        showCircle = true
        delay(500)
        showAppName = true
        delay(500)
        showTagline = true
        delay(1000)
        if (currentUser==null) {
            navController.navigate(WELCOME_SCREEN){
                popUpTo(SPLASH_SCREEN){
                    inclusive = true
                }
            }
        }
        else{
            navController.navigate(GALLERY_SCREEN){
                popUpTo(SPLASH_SCREEN){
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val darkPurple = Color(0xFFFF2DF1)
            val opacity = 0.7f

            drawCircle(
                color = darkPurple.copy(alpha = opacity),
                radius = circle1Radius.value.dp.toPx(),
                center = Offset(size.width * 0.25f, size.height * 0.25f)
            )

            drawCircle(
                color = darkPurple.copy(alpha = opacity),
                radius = circle2Radius.value.dp.toPx(),
                center = Offset(size.width * 0.75f, size.height * 0.25f)
            )

            drawCircle(
                color = darkPurple.copy(alpha = opacity),
                radius = circle3Radius.value.dp.toPx(),
                center = Offset(size.width * 0.25f, size.height * 0.75f)
            )

            drawCircle(
                color = darkPurple.copy(alpha = opacity),
                radius = circle4Radius.value.dp.toPx(),
                center = Offset(size.width * 0.75f, size.height * 0.75f)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(130.dp)
                    .scale(circleScale.value)
                    .alpha(circleAlpha.value)
                    .border(4.dp, Color.White, CircleShape)
            ) {
                Image(
                    painter = painterResource(R.drawable.app_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(100.dp)
                        .scale(logoScale.value)
                        .alpha(logoAlpha.value)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = showAppName,
                enter = fadeIn(animationSpec = tween(1000)) +
                        slideInVertically(animationSpec = tween(1000)) { it / 2 }
            ) {
                Text(
                    text = "JourneySnap",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = showTagline,
                enter = fadeIn(animationSpec = tween(1000))
            ) {
                Text(
                    text = "Memories of your journey",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}