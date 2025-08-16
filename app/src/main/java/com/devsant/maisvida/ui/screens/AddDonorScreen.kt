package com.devsant.maisvida.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devsant.maisvida.viewmodel.DonorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDonorScreen(
    navController: NavHostController,
    viewModel: DonorViewModel = viewModel()
) {
    val bloodTypes = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

    Scaffold(
        topBar =  {
            TopAppBar(
                title = {
                    Text("Create Donor Card", color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ){ innerPadding ->

        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            Card(
                modifier = Modifier,
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                TextField(
                    value = viewModel.name.value,
                    onValueChange = { viewModel.name.value = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            OutlinedTextField(
                value = viewModel.name.value,
                onValueChange = { viewModel.name.value = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )

            BloodTypeDropdown(
                expanded = viewModel.bloodTypeExpanded.value,
                onExpandedChange = { viewModel.bloodTypeExpanded.value = it },
                selectedType = viewModel.bloodType.value,
                onTypeSelected = { viewModel.bloodType.value = it },
                bloodTypes = bloodTypes
            )

            // Add other fields (lastDonation, city)

            Button(
                onClick = {
                    viewModel.addDonor()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Donor")
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodTypeDropdown(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    selectedType: String,
    onTypeSelected: (String) -> Unit,
    bloodTypes: List<String>,
    modifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedType,
            onValueChange = {},
            readOnly = true,
            label = { Text("Blood Type") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            bloodTypes.forEach { bloodType ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        color = when (bloodType.last()) {
                                            '+' -> Color(0xFFFFEBEE)
                                            '-' -> Color(0xFFE8F5E9)
                                            else -> Color(0xFFEEEEEE)
                                        },
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = bloodType,
                                    fontWeight = FontWeight.Bold,
                                    color = when (bloodType.last()) {
                                        '+' -> Color(0xFFD32F2F)
                                        '-' -> Color(0xFF388E3C)
                                        else -> Color(0xFF212121)
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = when (bloodType) {
                                    "O-" -> "Universal Donor"
                                    "AB+" -> "Universal Recipient"
                                    else -> ""
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    onClick = {
                        onTypeSelected(bloodType)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddDonorScreenPreview() {
    val navController = rememberNavController()
    AddDonorScreen(navController = navController)
}

