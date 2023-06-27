package com.example.seccraft_app.screens.profile


import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.seccraft_app.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.seccraft_app.Accompanist
import com.example.seccraft_app.BottomBarScreen
import com.example.seccraft_app.Collection.User.DataUser
import com.example.seccraft_app.ui.theme.PoppinsFamily
import com.example.seccraft_app.ui.theme.bg
import com.example.seccraft_app.ui.theme.gray_88
import com.example.seccraft_app.ui.theme.lightblue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.jet.firestore.JetFirestore

private var dataNumber = ""
private var dataName = ""
private var dataUri : Uri? = null

@Composable
fun EditProfileScreen(navController: NavHostController) {
    Accompanist().TopBar(color = bg)
    val auth = Firebase.auth
    var user by remember { mutableStateOf(DataUser()) }
    JetFirestore(
        path = { document("users/${auth.currentUser!!.uid}") },
        onRealtimeDocumentFetch = { value, exception ->
            val name = value?.getString("name").toString()
            val number = value?.getString("number").toString()
            val email = value?.getString("email").toString()
            val image = value?.getString("image").toString()
            user = user.copy(name = name, number = number, email = email, image = image)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(bg)
        ) {
            TopEditProfile(navController, user)
            PhotoProfile(auth)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                EditNama(user.name)
                EditNomor(user.number)
            }

        }
    }
}

private fun UpdateProfile(
    dataUser: DataUser,
    navController: NavHostController
) {
    val db = Firebase.firestore
    val auth = Firebase.auth
    val currentUser = auth.currentUser

    val storageReference = FirebaseStorage.getInstance().reference
    val loc = storageReference.child("ProfileUser/${auth.currentUser!!.uid}/PhotoProfile")
    val uploadTask = loc.putFile(dataUri!!)

    Log.d("ISI URI", "UpdateProfile: ${dataUser.name}")

    uploadTask.addOnSuccessListener { taskSnapshot ->
        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
            val data = dataUser.copy(image = it.toString(), name = dataName, number = dataNumber)
            db.collection("users").document(currentUser?.uid!!)
                .set(data)
                .addOnSuccessListener { documentReference ->
                    navController.navigate(BottomBarScreen.Profil.route)
                }
                .addOnFailureListener { e ->

                }
        }

    }.addOnFailureListener {
//        Toast.makeText(context, "Failed to Upload", Toast.LENGTH_SHORT).show()
//        if (progressDialog.isShowing) {
//            progressDialog.dismiss()
//        }
    }

}

@Composable
fun EditNomor(number: String) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.telp),
            fontFamily = PoppinsFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            color = gray_88
        )
        var inp_num by remember(number) { mutableStateOf(number) }
        OutlinedTextField(
            value = inp_num,
            onValueChange = { inp_num = it },
            textStyle = TextStyle(
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            modifier = Modifier
                .padding(top = 8.dp)
                .wrapContentHeight()
                .fillMaxWidth()
        )
        dataNumber = inp_num
    }
}

@Composable
fun EditNama(name: String) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.nama),
            fontFamily = PoppinsFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            color = gray_88
        )
        var inp_name by remember(name) { mutableStateOf(name) }
        OutlinedTextField(
            value = inp_name,
            onValueChange = { inp_name = it },
            textStyle = TextStyle(
                color = Color.Black,
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            modifier = Modifier
                .padding(top = 8.dp)
                .wrapContentHeight()
                .fillMaxWidth()
        )
        dataName = inp_name
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoProfile(auth: FirebaseAuth) {
    var user by remember { mutableStateOf(DataUser()) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
            dataUri = imageUri
        }

    //Log.d("IMAGE URI:", "ISINYA: $imageUri")

    JetFirestore(
        path = { document("users/${auth.currentUser!!.uid}") },
        onRealtimeDocumentFetch = { value, exception ->
            val image = value!!.getString("image").toString()
            user = user.copy(image = image)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        ) {
            Card(modifier = Modifier.size(64.dp), colors = CardDefaults.cardColors(bg)) {
                Log.d("Data URI", "ISI DATA URI: $imageUri")
                if (user.image == "" && dataUri == null) {
                    Image(
                        painter = painterResource(id = R.drawable.user_profile),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                } else if(dataUri != null){
                    Image(
                        painter = rememberAsyncImagePainter(model = dataUri),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
                else {
                    Image(
                        painter = rememberAsyncImagePainter(model = user.image),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
            }

            Text(
                text = stringResource(id = R.string.edit_foto),
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = lightblue,
                style = LocalTextStyle.current.copy(
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                modifier = Modifier
                    .padding(top = 14.dp)
                    .clickable { launcher.launch("image/*") }
            )
        }
    }
}


@Composable
fun TopEditProfile(navController: NavHostController, user: DataUser) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Icon(
            painter = painterResource(id = R.drawable.close),
            contentDescription = "",
            modifier = Modifier.clickable { navController.navigate(BottomBarScreen.Profil.route) })
        Text(
            text = stringResource(id = R.string.edit_profil),
            fontFamily = PoppinsFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            style = LocalTextStyle.current.copy(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.checklist),
            contentDescription = "",
            tint = lightblue,
            modifier = Modifier.clickable { UpdateProfile(user, navController) }
        )
    }
}


//@Preview
//@Composable
//fun EditProfileScreenPrev() {
//    EditProfileScreen(navController)
//}
