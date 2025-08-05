package com.pucetec.timeformed.model

data class TreatmentMed(
    val id: Long? = null,
    val treatmentId: Long,
    val medId: Long,
    val dose: String,
    val frequencyHours: Int,
    val durationDays: Int,
    val startHour: String   // formato "HH:mm" (ej: "08:00")
)
