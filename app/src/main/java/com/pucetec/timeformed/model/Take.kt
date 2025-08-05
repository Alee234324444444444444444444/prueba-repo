package com.pucetec.timeformed.model

data class Take(
    val id: Long? = null,
    val treatmentMedId: Long,
    val scheduledDateTime: String,  // formato ISO "2025-06-17T08:00:00"
    val takenDateTime: String?,     // puede ser null si no se tomó aún
    val wasTaken: Boolean
)
