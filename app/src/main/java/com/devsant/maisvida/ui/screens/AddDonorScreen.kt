package com.devsant.maisvida.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devsant.maisvida.ui.components.CardInputField
import com.devsant.maisvida.ui.components.DateInputField
import com.devsant.maisvida.ui.components.DatePickerField
import com.devsant.maisvida.ui.components.IdentificationField
import com.devsant.maisvida.viewmodel.DonorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDonorScreen(
    navController: NavHostController,
    viewModel: DonorViewModel = viewModel()
) {
    var showDatePicker by remember { mutableStateOf(false) }
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

            CardInputField {
                TextField(
                    value = viewModel.name.value,
                    onValueChange = { viewModel.name.value = it },
                    label = { Text("Full Name", color = Color.White, fontWeight = FontWeight.Bold) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CardInputField {
                IdentificationField(
                    value = viewModel.identification.value,
                    onValueChange = { viewModel.identification.value = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CardInputField {
                BloodTypeDropdown(
                    expanded = viewModel.bloodTypeExpanded.value,
                    onExpandedChange = { viewModel.bloodTypeExpanded.value = it },
                    selectedType = viewModel.bloodType.value,
                    onTypeSelected = { viewModel.bloodType.value = it },
                    bloodTypes = bloodTypes
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            CardInputField {
                DateInputField(
                    value = viewModel.lastDonation.value,
                    onClick = { showDatePicker  = true }
                )
                DatePickerField(
                    showDialog = showDatePicker,
                    onDismiss = { showDatePicker = false },
                    onDateSelected = {
                        viewModel.lastDonation.value = it
                        showDatePicker = false
                    }
                )
            }

            Spacer(modifier = Modifier.size(20.dp))

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

        TextField(
            value = selectedType,
            onValueChange = {},
            readOnly = true,
            label = { Text("Blood Type", color = Color.White, fontWeight = FontWeight.Bold) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
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

