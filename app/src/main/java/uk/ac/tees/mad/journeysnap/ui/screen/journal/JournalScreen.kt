package uk.ac.tees.mad.journeysnap.ui.screen.journal

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import uk.ac.tees.mad.journeysnap.R
import uk.ac.tees.mad.journeysnap.ui.components.HeadingContainer
import uk.ac.tees.mad.journeysnap.ui.components.ImageContainer
import uk.ac.tees.mad.journeysnap.utils.Utils.saveBitmapToCache
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    navController: NavController,
    viewModel: JournalViewModel = hiltViewModel()) {
    var name by remember { mutableStateOf("") }
    var story by remember { mutableStateOf("") }
    var startDate by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var endDate by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var location by remember { mutableStateOf("") }
    val imageList by viewModel.imageList.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    var dateState by remember { mutableIntStateOf(0) }
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val uri = saveBitmapToCache(context, bitmap)
            viewModel.addImage(uri)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        viewModel.addImage(uris)
    }

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
                    title = "Location",
                    icon = R.drawable.map,
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
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
                        value = story,
                        onValueChange = { story = it },
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
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .background(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.LightGray.copy(0.4f)
                                )
                                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                                .weight(1f)
                                .clickable {
                                    dateState = 1
                                    showDatePicker = true
                                }
                        ) {
                            Text(
                                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                                    Date(
                                        startDate
                                    )
                                ), modifier = Modifier.padding(16.dp)
                            )
                        }
                        Text("To", modifier = Modifier.padding(12.dp))
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .background(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.LightGray.copy(0.4f)
                                )
                                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                                .weight(1f)
                                .clickable {
                                    dateState = 2
                                    showDatePicker = true
                                }
                        ) {
                            Text(
                                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                                    Date(
                                        endDate
                                    )
                                ),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
                HeadingContainer(
                    title = "Photos (${imageList.size})",
                    icon = R.drawable.image,
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    LazyRow(modifier = Modifier.padding(start = 16.dp)) {

                        items(imageList) { uri ->
                            ImageContainer(uri)
                        }
                        item {
                            Box(contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .size(100.dp)
                                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                                    .padding(2.dp)
                                    .clickable {
                                        showSheet = true
                                    }
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.add_image),
                                    contentDescription = "image",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                TextButton(
                    {
                        viewModel.addJournal(
                            name, location, story, endDate, startDate, context,
                            onComplete = {
                                navController.popBackStack()
                            }
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        "Add",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("CANCEL")
                    }
                }
            ) {
                val datePickerState = rememberDatePickerState()
                DatePicker(
                    state = datePickerState
                )

                LaunchedEffect(datePickerState.selectedDateMillis) {
                    datePickerState.selectedDateMillis?.let { millis ->
                        if (dateState == 1) {
                            startDate = millis
                        }
                        if (dateState == 2) {
                            endDate = millis
                        }
                    }
                }
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Choose an Option", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        showSheet = false
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        } else {
                            cameraLauncher.launch()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_camera_alt_24),
                        contentDescription = "Camera", tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Open Camera", color = Color.White)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        showSheet = false
                        galleryLauncher.launch("image/*")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_photo_24),
                        contentDescription = "Gallery", tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Select from Gallery", color = Color.White)
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}