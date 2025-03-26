package uk.ac.tees.mad.journeysnap.ui.screen.gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import uk.ac.tees.mad.journeysnap.utils.Constants

@Composable
fun GalleryScreen(navController: NavController) {
    Scaffold(
        topBar = {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 30.dp, horizontal = 16.dp)
                ) {
                Text("My Gallery",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.weight(1f)
                    )
                IconButton({navController.navigate(Constants.SETTING_SCREEN)}) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "settings"
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton({navController.navigate(Constants.JOURNAL_SCREEN)},
                modifier = Modifier.padding(bottom = 30.dp, end = 16.dp)) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "add new"
                )
            }
        }
    ) { paddingValues ->
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.padding(paddingValues).fillMaxSize()){
            Text("Gallery Screen")
        }
    }
}