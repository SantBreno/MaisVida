package com.devsant.maisvida.model

data class Donor(
    val id: Int = 0,
    val name: String,
    val identification: String,
    val bloodType: String,
    val birthDate: String,
    val lastDonation: String,
    val canDonate: Boolean,
)