package uk.ac.tees.mad.journeysnap.ui.screen.setting

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import uk.ac.tees.mad.journeysnap.R
import uk.ac.tees.mad.journeysnap.ui.components.EditProfileBottomSheet
import uk.ac.tees.mad.journeysnap.utils.Constants
import uk.ac.tees.mad.journeysnap.utils.Utils

@Composable
fun SettingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val showEditProfile = remember { mutableStateOf(false) }
    val tempImage = remember { mutableStateOf<Bitmap?>(null) }
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val profileImage by viewModel.profileImage.collectAsState()

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
            tempImage.value = bitmap
        }
    }
    Scaffold(
        topBar = {
            Text(
                "Settings",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 30.dp)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Card(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        AsyncImage(
                            model = Utils.base64ToBitmap(profileImage),
                            contentDescription = "profile image",
                            placeholder = painterResource(R.drawable.profile),
                            error = painterResource(R.drawable.profile),
                            modifier = Modifier
                                .size(76.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.Gray)
                        )
                        Column(
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(1f)
                        ) {
                            Text(
                                name,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                email,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                Card(onClick = {showEditProfile.value = true}, modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Edit Profile",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
                Card(onClick = {
                    viewModel.logOut {
                        navController.navigate(Constants.WELCOME_SCREEN){
                            popUpTo(0)
                        }
                    }
                }, modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Log out",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
            }
        }
        if (showEditProfile.value){
            EditProfileBottomSheet(
                name,
                tempImage.value,
                onSave = {
                    viewModel.changeProfile(it, tempImage.value)
                    showEditProfile.value = false
                },
                onImageClick = {
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
                onDismiss = {
                    showEditProfile.value = false
                }
            )
        }
    }
}