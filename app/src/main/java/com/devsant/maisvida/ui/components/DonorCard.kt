package com.devsant.maisvida.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devsant.maisvida.model.Donor

@Composable
fun DonorCard(
    donor: Donor,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable  ( onClick = onClick )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BloodTypeBadge(bloodType = donor.bloodType, isEligible = donor.canDonate)

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = donor.name, fontWeight = FontWeight.Bold)
                Text(
                    "Last donation: ${donor.lastDonation}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun BloodTypeBadge(
    bloodType: String,
    isEligible: Boolean
){
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                color = if (isEligible) Color.Green else Color.Red,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = bloodType,
            color = if (isEligible) Color.White else Color.Blue,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DonorCardPreview() {
    val sampleDonor = Donor(
        name = "John Doe",
        bloodType = "+O",
        canDonate = true,
        lastDonation = "2023-11-15",
        identification = "12345678901",
        birthDate = "1990-01-01"
    )
    DonorCard(donor = sampleDonor)
}