package uk.ac.tees.mad.journeysnap.ui.screen.gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun GalleryScreen(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()){
        Text("Gallery Screen")
    }
}