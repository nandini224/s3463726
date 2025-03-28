package uk.ac.tees.mad.journeysnap.ui.screen.journal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.journeysnap.R
import uk.ac.tees.mad.journeysnap.ui.components.HeadingContainer
import uk.ac.tees.mad.journeysnap.ui.components.ImageContainer

@Composable
fun JournalScreen() {
    var name by remember { mutableStateOf("") }
    var imageCount by remember { mutableIntStateOf(0) }
    Scaffold(
        topBar = {
            Text(
                "Add New Memory",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 30.dp)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                HeadingContainer(
                    title = "Journey Name",
                    icon = R.drawable.trip_icon,
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.LightGray.copy(0.4f)
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth()
                    )
                }
                HeadingContainer(
                    title = "Story",
                    icon = R.drawable.info_icon,
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.LightGray.copy(0.4f)
                        ),
                        maxLines = 3,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth()
                    )
                }
                HeadingContainer(
                    title = "Dates",
                    icon = R.drawable.calendar,
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    Row {
                        OutlinedTextField(
                            value = "12/02/2018",
                            onValueChange = { name = it },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = Color.LightGray.copy(0.4f)
                            ),
                            readOnly = true,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f)
                        )
                        OutlinedTextField(
                            value = "12/02/2018",
                            onValueChange = { name = it },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = Color.LightGray.copy(0.4f)
                            ),
                            readOnly = true,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f)
                        )
                    }
                }
                HeadingContainer(
                    title = "Location",
                    icon = R.drawable.map,
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.LightGray.copy(0.4f)
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth()
                    )
                }
                HeadingContainer(
                    title = "Photos ($imageCount)",
                    icon = R.drawable.image,
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    LazyRow(modifier = Modifier.padding(start = 16.dp)) {
                        items(imageCount) {
                            ImageContainer(R.drawable.app_logo)
                        }
                        item {
                            ImageContainer(R.drawable.add_image,
                                modifier = Modifier.clickable {
                                    imageCount++
                                }
                            )
                        }
                    }
                }

                TextButton({},
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                    ) {
                    Text("Add",
                        style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}