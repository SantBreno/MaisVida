package com.devsant.maisvida.viewmodel


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devsant.maisvida.model.Donor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DonorViewModel: ViewModel() {
    val name = mutableStateOf("")
    val identification = mutableStateOf("")
    val bloodType = mutableStateOf("")
    val bloodTypeExpanded = mutableStateOf(false)
    val birthDate = mutableStateOf("")
    val lastDonation = mutableStateOf("")

    val donors = mutableStateListOf<Donor>()

    fun addDonor() {
        if (isFormValid()) {
            donors.add(
                Donor(
                    name = name.value,
                    identification = identification.value,
                    bloodType = bloodType.value,
                    lastDonation = lastDonation.value,
                    birthDate = birthDate.value,
                    canDonate = checkEligibility(lastDonation.value)
                )
            )
            resetForm()
        }
        }


        private fun isFormValid(): Boolean {
        return name.value.isNotBlank() &&
                bloodType.value.isNotBlank() &&
                lastDonation.value.isNotBlank()
    }

    fun checkEligibility(lastDonation: String): Boolean {
        if (lastDonation.isBlank()) return true // Never donated before

        try {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val lastDonationDate = dateFormat.parse(lastDonation)
            val today = Calendar.getInstance().time

            // Calculate difference in days
            val diff = today.time - (lastDonationDate?.time ?: return false)
            val daysSinceLastDonation = diff / (1000 * 60 * 60 * 24)

            // Men can donate every 60 days, women every 90 days
            // Using 60 days as default since we don't have gender info
            return daysSinceLastDonation >= 60
        } catch (e: Exception) {
            return false
        }
    }

    private fun resetForm() {
        name.value = ""
        identification.value = ""
        bloodType.value = ""
        lastDonation.value = ""
        birthDate.value = ""
        bloodTypeExpanded.value = false
    }
}