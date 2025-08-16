package com.devsant.maisvida.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devsant.maisvida.model.Donor

class DonorViewModel: ViewModel() {
    val name = mutableStateOf("")
    val bloodType = mutableStateOf("")
    val bloodTypeExpanded = mutableStateOf(false)
    val lastDonation = mutableStateOf("")

    val donors = mutableStateListOf<Donor>()

    fun addDonor() {
        donors.add(
            Donor(
                name = name.value,
                bloodType = bloodType.value,
                lastDonation = lastDonation.value,
                canDonate = checkEligibility(lastDonation.value)
            )
        )
        resetForm()
    }

    private fun checkEligibility(lastDonation: String): Boolean {
        return true
    }

    private fun resetForm() {
        name.value = ""
        bloodType.value = ""
        lastDonation.value = ""
    }
}