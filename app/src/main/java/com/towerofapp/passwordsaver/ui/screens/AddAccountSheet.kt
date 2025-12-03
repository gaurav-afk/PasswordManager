package com.towerofapp.passwordsaver.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.towerofapp.passwordsaver.viewmodel.AddAccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountSheet(addVM: AddAccountViewModel, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                isError = nameError,
                onValueChange = {
                    name = it
                    nameError = false
                },
                label = { Text("Account Name") }
            )
            if (nameError) Text("Name cannot be empty", color = Color.Red, fontSize = TextUnit(12f, TextUnitType.Sp))

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                isError = emailError,
                onValueChange = {
                    email = it
                    emailError = false
                },
                label = { Text("Username/Email") }
            )
            if (emailError) Text("Username/Email cannot be empty", color = Color.Red, fontSize = TextUnit(12f, TextUnitType.Sp))

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError,
                onValueChange = {
                    password = it
                    passwordError = false
                },
                label = { Text("Password") }
            )
            if (passwordError) Text("Password cannot be empty", color = Color.Red, fontSize = TextUnit(12f, TextUnitType.Sp))

            Spacer(Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                onClick = {
                    val isNameEmpty = name.isBlank()
                    val isEmailEmpty = email.isBlank()
                    val isPasswordEmpty = password.isBlank()

                    nameError = isNameEmpty
                    emailError = isEmailEmpty
                    passwordError = isPasswordEmpty

                    if (!isNameEmpty && !isEmailEmpty && !isPasswordEmpty) {
                        addVM.addAccount(name, email, password)
                        onDismiss()
                    }
                }
            ) {
                Text(
                    fontWeight = FontWeight.ExtraBold,
                    text = "Add New Account",
                    fontSize = TextUnit(18f, TextUnitType.Sp)
                )
            }
        }
    }
}
