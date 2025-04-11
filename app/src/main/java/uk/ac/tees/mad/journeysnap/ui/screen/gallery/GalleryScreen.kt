package uk.ac.tees.mad.journeysnap.ui.screen.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import uk.ac.tees.mad.journeysnap.model.JournalEntity
import uk.ac.tees.mad.journeysnap.ui.components.JournalItem
import uk.ac.tees.mad.journeysnap.utils.Constants

@Composable
fun GalleryScreen(navController: NavController) {
    val searchQuery = remember { mutableStateOf("") }
    val journal = JournalEntity(
        id = "",
        name = "Holi Festive 2025",
        story = "Lots of color and joy Lots of color and joy Lots of " +
                "color and joy Lots of color and joy Lots of color and joy",
        location = "Patna, bihar, India",
        startDate = System.currentTimeMillis(),
        endDate = System.currentTimeMillis(),
        image = ""
    )
    Scaffold(
        topBar = {
            Column(modifier = Modifier.padding(top = 30.dp, bottom = 12.dp,start = 16.dp, end = 16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "My Gallery",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton({ navController.navigate(Constants.SETTING_SCREEN) }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "settings"
                        )
                    }
                }

                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = {searchQuery.value = it},
                    leadingIcon = { Icon(
                        Icons.Default.Search,
                        contentDescription = "Search"
                    ) },
                    placeholder = { Text("Search..") },
                    modifier = Modifier.fillMaxWidth()
                )

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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
            items(10){
                JournalItem(journal, modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}