package uk.ac.tees.mad.journeysnap.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun HeadingContainer(
    title:String,
    icon:Int,
    modifier: Modifier = Modifier,
    content:@Composable ()->Unit) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "icon",
                modifier = Modifier.size(28.dp)
            )
            Text(title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp))
        }
        content()
    }
}