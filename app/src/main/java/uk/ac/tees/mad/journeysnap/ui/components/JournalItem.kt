package uk.ac.tees.mad.journeysnap.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import uk.ac.tees.mad.journeysnap.model.JournalEntity
import uk.ac.tees.mad.journeysnap.utils.Utils
import uk.ac.tees.mad.journeysnap.utils.Utils.getDate

@Composable
fun JournalItem(journal: JournalEntity, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .fillMaxWidth()
        .height(250.dp)) {
        Row(
            modifier.fillMaxSize()
        ) {
            Utils.base64ToBitmap(journal.image)?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.weight(2f)
                        .fillMaxHeight()
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(3f)
            ) {
                Text(
                    journal.name,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Location"
                    )
                    Text(journal.location, modifier = Modifier.padding(start = 4.dp))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Date",
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        getDate(journal.startDate) + " - "+ getDate(journal.endDate),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Text(
                    journal.story,
                    maxLines = 3,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 12.dp)
                )
                Row {
                    IconButton({}) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Delete"
                        )
                    }
                    Spacer(Modifier.width(30.dp))
                    IconButton({}) {
                        Icon(
                            Icons.Outlined.Share,
                            contentDescription = "Share"
                        )
                    }
                }
            }
        }
    }
}