package uk.ac.tees.mad.journeysnap.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ImageContainer(image:Int,modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(end = 12.dp)
        .size(100.dp)
        .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
        .padding(2.dp)
    ){
        Image(
            painter = painterResource(image),
            contentDescription = "image",
            contentScale = ContentScale.Crop
        )
    }
}