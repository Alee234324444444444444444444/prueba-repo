package com.pucetec.timeformed.model

data class Treatment(
    val id: Long? = null,
    val name: String,
    val description: String,
    val userId: Long   // el id del usuario dueño del tratamiento
)
