package com.example.shopna.presentation.view.home


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.shopna.R
import com.example.shopna.data.model.EditProfileRequest
import com.example.shopna.presentation.view_model.AuthViewModel
import com.example.shopna.ui.theme.backgroundColor
import com.example.shopna.ui.theme.kPrimaryColor
import com.example.shopna.ui.theme.lightGreyColor


class EditProfile(private val authViewModel: AuthViewModel) : Screen {

    @Composable
    override fun Content() {

        val scrollState = rememberScrollState()
        val userState = authViewModel.user.collectAsState()
        val isLoading = authViewModel.isLoading.collectAsState()
        val selectedField= remember {
            mutableIntStateOf(0)
        }

        val name = remember { mutableStateOf(userState.value?.data?.name ?: "") }
        val email = remember { mutableStateOf(userState.value?.data?.email ?: "") }
        val phone = remember { mutableStateOf(userState.value?.data?.phone ?: "") }

        val originalName = userState.value?.data?.name ?: ""
        val originalEmail = userState.value?.data?.email ?: ""
        val originalPhone = userState.value?.data?.phone ?: ""

        var showDialog by remember { mutableStateOf(false) }
        var dialogMessage by remember { mutableStateOf("") }

        val imageUrl = rememberSaveable { mutableStateOf(userState.value?.data?.image ?: "") }
        val painter = rememberAsyncImagePainter(imageUrl.value)

        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUrl.value = it.toString()
            }
        }
        val navigator = LocalNavigator.currentOrThrow


        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Warning") },
                text = { Text(dialogMessage) }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding()
            ) {
                IconButton(
                    onClick = {
                        navigator.pop()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back to Home",
                        tint = Color.Black.copy(0.7f),
                        modifier = Modifier.size(35.dp)


                    )
                }

                Text(
                    text = "Edit Profile",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.interregular))
                    ),
                    textAlign = TextAlign.Center
                )

            }


                Column(modifier = Modifier
                    .fillMaxWidth()
                   , horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Box(modifier = Modifier.size(120.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter("https://student.valuxapps.com/storage/assets/defaults/user.jpg"),
                            contentDescription = "User Profile Picture",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .border(3.dp, kPrimaryColor, CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.pen),
                            contentDescription = "Edit Profile Picture",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.BottomEnd)
                                .clickable {
                                    launcher.launch("image/*")

                                }
                                .background(backgroundColor, shape = CircleShape)
                                .padding(5.dp),
                            tint = kPrimaryColor
                        )
                    }
                }

            Spacer(modifier = Modifier.height(16.dp))


            CustomEditTextField(
                text = "Username",
                value = name,
                error = name.value.isEmpty(),
                errorMessage = "Username cannot be empty",
                onClick = {

                    if (name.value == originalName) {
                        dialogMessage = "You didn't change the name."
                        showDialog = true
                    } else {
                        selectedField.intValue = 1

                        authViewModel.editProfile(
                            editProfileRequest = EditProfileRequest(
                                image = userState.value?.data?.image.toString(),
                                name = name.value,
                                email = userState.value?.data?.email.toString(),
                                phone = userState.value?.data?.phone.toString()

                            )
                        )
                    }

                },
                isLoading = if(selectedField.intValue==1) isLoading.value else false
            )

            CustomEditTextField(
                text = "Email",
                value = email,
                error = email.value.isEmpty(),
                errorMessage = "Email cannot be empty",
                keyboardType = KeyboardType.Email,
                onClick = {

                    if (email.value == originalEmail) {
                        dialogMessage = "You didn't change the email."
                        showDialog = true
                    } else {
                        selectedField.intValue=2
                        authViewModel.editProfile(
                            editProfileRequest = EditProfileRequest(
                                image = userState.value?.data?.image.toString(),
                                name = userState.value?.data?.name.toString(),
                                email = email.value,
                                phone = userState.value?.data?.phone.toString()

                            )
                        )
                    }

                },
                isLoading = if(selectedField.intValue==2) isLoading.value else false

            )

            CustomEditTextField(
                text = "Mobile Phone",
                value = phone,
                error = phone.value.isEmpty(),
                errorMessage = "Mobile phone cannot be empty",
                keyboardType = KeyboardType.Phone,
                onClick = {

                        if (phone.value == originalPhone) {
                            dialogMessage = "You didn't change the phone."
                            showDialog = true
                        } else {
                            selectedField.intValue=3
                            authViewModel.editProfile(
                                editProfileRequest = EditProfileRequest(
                                    image = userState.value?.data?.image.toString(),
                                    name = userState.value?.data?.name.toString(),
                                    email = userState.value?.data?.email.toString(),
                                    phone = phone.value

                                )
                            )
                        }

                },
                isLoading = if(selectedField.intValue==3) isLoading.value else false

            )
        }
    }
}

@Composable
fun CustomEditTextField(
    text: String,
    value: MutableState<String>,
    error: Boolean,
    errorMessage: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onClick: () -> Unit = {},
    isLoading: Boolean
) {
    Column(Modifier.padding(horizontal = 16.dp)) {

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, style = TextStyle(fontSize = 16.sp,color = kPrimaryColor))
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                trailingIcon = {
                   if(isLoading) CircularProgressIndicator(color = kPrimaryColor, modifier = Modifier.size(13.dp)) else IconButton(onClick = onClick,modifier=Modifier.size(16.dp), colors = IconButtonDefaults.iconButtonColors(containerColor = kPrimaryColor)) {

                       Icon(
                           Icons.Default.Check, contentDescription = "",
                           tint = Color.White,
                           modifier = Modifier.padding(2.dp)


                           )

                   }
                },
                enabled = true,
                modifier = Modifier.weight(1f),
                value = value.value,
                onValueChange = { value.value = it },
                shape = RoundedCornerShape(25),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = lightGreyColor,
                    unfocusedBorderColor = lightGreyColor,
                    errorBorderColor = kPrimaryColor,
                    errorTextColor = kPrimaryColor,
                    cursorColor = kPrimaryColor
                ),
                isError = error,
            )




        }

        if (error) {
            Text(
                text = errorMessage,
                color = kPrimaryColor,
                style = TextStyle(fontSize = 12.sp)
            )
        }
    }
}




