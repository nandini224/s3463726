package uk.ac.tees.mad.journeysnap.ui.screen.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import uk.ac.tees.mad.journeysnap.model.JournalEntity
import uk.ac.tees.mad.journeysnap.ui.components.JournalItem
import uk.ac.tees.mad.journeysnap.utils.Constants

@Composable
fun GalleryScreen(navController: NavController, viewModel: GalleryViewModel= hiltViewModel()) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val journalList by viewModel.journalList.collectAsState()
    val context = LocalContext.current
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
                    value = searchQuery,
                    onValueChange = {viewModel.onQueryChange(it)},
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
                .padding(horizontal = 16.dp)
                .fillMaxSize()) {
            items(journalList){journal->
                JournalItem(journal,
                    onDelete = {viewModel.deleteJournal(journal, context)},
                    onShare = {viewModel.shareImage(context,journal.image)}
                )
            }
        }
    }
}