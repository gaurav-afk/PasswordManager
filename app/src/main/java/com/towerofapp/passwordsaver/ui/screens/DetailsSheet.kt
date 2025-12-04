package com.towerofapp.passwordsaver.ui.screens

import com.towerofapp.passwordsaver.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.towerofapp.passwordsaver.data.model.AccountEntity

@Composable
fun DetailSheet(
    account: AccountEntity,
    onDismiss: () -> Unit,
    onEdit: (AccountEntity) -> Unit,
    onDelete: (AccountEntity) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }

    var canSeePass by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(account.name) }
    var email by remember { mutableStateOf(account.email) }
    var password by remember { mutableStateOf(account.password) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF8F3FB))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (isEditing) "Edit Account" else "Account Details",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF217EEA)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isEditing) {

            OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = name, onValueChange = { name = it }, label = { Text("Name") })
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = email, onValueChange = { email = it }, label = { Text("Email") })
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = password, onValueChange = { password = it }, label = { Text("Password") })
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Account Type
                SectionLabel("Account Type")
                SectionValue(account.name)

                Spacer(Modifier.height(10.dp))

                // Email
                SectionLabel("Username/ Email")
                SectionValue(account.email)
                Spacer(Modifier.height(10.dp))

                // Password
                SectionLabel("Password")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (canSeePass) password else "********",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        painter = if (canSeePass)
                            painterResource(id = R.drawable.icon_visibility)
                        else
                            painterResource(id = R.drawable.icon_visibility_off),
                        contentDescription = null,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable { canSeePass = !canSeePass },
                        tint = Color(0xFF8A8A8A)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isEditing) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    onClick = {
                    val updatedAccount = account.copy(name = name, email = email, password = password)
                    onEdit(updatedAccount)
                    isEditing = false
                }) {
                    Text("Save")
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // optional spacing between buttons
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { isEditing = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Edit")
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { onDelete(account); onDismiss() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Delete", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall.copy(
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
    )
}

@Composable
private fun SectionValue(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold
        )
    )
}