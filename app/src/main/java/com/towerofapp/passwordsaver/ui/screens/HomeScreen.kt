package com.towerofapp.passwordsaver.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.towerofapp.passwordsaver.model.AccountEntity
import com.towerofapp.passwordsaver.ui.components.AccountItem
import com.towerofapp.passwordsaver.viewmodel.AddAccountViewModel
import com.towerofapp.passwordsaver.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeVM: HomeViewModel,
    addVM: AddAccountViewModel
) {
    val accounts by homeVM.accounts.collectAsState()

    var showAddSheet by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf<AccountEntity?>(null) }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color(0xFF217EEA),
                onClick = { showAddSheet = true }
            ) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            Text(
                text = "Password Manager",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(accounts) { account ->
                    AccountItem(
                        account = account,
                        onClick = { selectedAccount = account }
                    )
                }
            }
        }
    }

    if (showAddSheet) {
        AddAccountSheet(
            addVM = addVM,
            onDismiss = { showAddSheet = false }
        )
    }

    selectedAccount?.let { account ->
        ModalBottomSheet(
            onDismissRequest = { selectedAccount = null },
            sheetState = bottomSheetState
        ) {
            DetailSheet(
                account = account,
                onDelete = {
                    homeVM.deleteAccount(account)
                    selectedAccount = null
                },
                onDismiss = { selectedAccount = null },
                onEdit = { updatedAccount ->
                    addVM.updateAccount(updatedAccount) // <-- calls update
                    selectedAccount = null
                }
            )
        }
    }

}
