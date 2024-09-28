

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.firebase.storage.FirebaseStorage
import com.task.expencetracker.uicomponents.ExpenseTextView
import java.util.UUID

@Composable
fun ProfileScreen(
    userName: String,
    userEmail: String,
    profilePictureUrl: String?,
    onLogoutClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onImageSelected: (Uri) -> Unit
) {

    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Image Picker
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let { onImageSelected(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Profile Picture
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
                .clickable {
                    launcher.launch("image/*")  // Launch the image picker when clicked
                },
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                Image(
                    painter = rememberImagePainter(data = selectedImageUri),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else if (profilePictureUrl != null) {
                Image(
                    painter = rememberImagePainter(data = profilePictureUrl),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Default Profile Picture",
                    modifier = Modifier.size(60.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User Name
        ExpenseTextView(
            text = userName,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        // User Email
        ExpenseTextView(
            text = userEmail,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Edit Profile Button
        Button(
            onClick = { onEditProfileClick() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            ExpenseTextView(text = "Edit Profile")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logout Button
        Button(
            onClick = { onLogoutClick() },
            modifier = Modifier.fillMaxWidth(),

            colors = ButtonDefaults.buttonColors(containerColor =  MaterialTheme.colorScheme.error)
        ) {
            ExpenseTextView(text = "Logout")
        }
    }
}


fun uploadImageToFirebase(uri: Uri, onSuccess: (String) -> Unit) {
    val storageRef = FirebaseStorage.getInstance().reference.child("profile_pictures/${UUID.randomUUID()}.jpg")
    val uploadTask = storageRef.putFile(uri)

    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            task.exception?.let { throw it }
        }
        storageRef.downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val downloadUri = task.result
            onSuccess(downloadUri.toString())  // The URL of the uploaded image
        }
    }
}