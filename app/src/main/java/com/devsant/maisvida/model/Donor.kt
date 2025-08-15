package com.devsant.maisvida.model

data class Donor(
    val id: Int = 0,
    val name: String,
    val bloodType: String,
    val lastDonation: String,
    val canDonate: Boolean,
)