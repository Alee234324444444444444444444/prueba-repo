package com.pucetec.timeformed.network

import com.pucetec.timeformed.model.*
import retrofit2.http.*
import com.pucetec.timeformed.model.dto.MedRequest


interface ApiService {

    // ========================
    // 📌 USERS
    // ========================

    @GET("api/timeformed/users")
    suspend fun getUsers(): List<User>

    @GET("api/timeformed/users/{id}")
    suspend fun getUser(@Path("id") id: Long): User

    @POST("api/timeformed/users")
    suspend fun createUser(@Body user: User): User

    @PUT("api/timeformed/users/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body user: User): User

    @DELETE("api/timeformed/users/{id}")
    suspend fun deleteUser(@Path("id") id: Long)


    // ========================
    // 📌 MEDS
    // ========================

    @GET("api/timeformed/meds")
    suspend fun getMeds(): List<Med>

    @GET("api/timeformed/meds/{id}")
    suspend fun getMed(@Path("id") id: Long): Med

    @POST("api/timeformed/meds")
    suspend fun createMed(@Body medRequest: MedRequest): Med

    @PUT("api/timeformed/meds/{id}")
    suspend fun updateMed(@Path("id") id: Long, @Body medRequest: MedRequest): Med

    @DELETE("api/timeformed/meds/{id}")
    suspend fun deleteMed(@Path("id") id: Long)



    // ========================
    // 📌 TREATMENTS
    // ========================

    @GET("api/timeformed/treatments")
    suspend fun getTreatments(): List<Treatment>

    @GET("api/timeformed/treatments/{id}")
    suspend fun getTreatment(@Path("id") id: Long): Treatment

    @POST("api/timeformed/treatments")
    suspend fun createTreatment(@Body treatment: Treatment): Treatment

    @PUT("api/timeformed/treatments/{id}")
    suspend fun updateTreatment(@Path("id") id: Long, @Body treatment: Treatment): Treatment

    @DELETE("api/timeformed/treatments/{id}")
    suspend fun deleteTreatment(@Path("id") id: Long)


    // ========================
    // 📌 TREATMENT-MEDS
    // ========================

    @GET("api/timeformed/treatment-meds")
    suspend fun getTreatmentMeds(): List<TreatmentMed>

    @GET("api/timeformed/treatment-meds/{id}")
    suspend fun getTreatmentMed(@Path("id") id: Long): TreatmentMed

    @POST("api/timeformed/treatment-meds")
    suspend fun createTreatmentMed(@Body treatmentMed: TreatmentMed): TreatmentMed

    @PUT("api/timeformed/treatment-meds/{id}")
    suspend fun updateTreatmentMed(@Path("id") id: Long, @Body treatmentMed: TreatmentMed): TreatmentMed

    @DELETE("api/timeformed/treatment-meds/{id}")
    suspend fun deleteTreatmentMed(@Path("id") id: Long)


    // ========================
    // 📌 TAKES
    // ========================

    @GET("api/timeformed/takes")
    suspend fun getTakes(): List<Take>

    @GET("api/timeformed/takes/{id}")
    suspend fun getTake(@Path("id") id: Long): Take

    @POST("api/timeformed/takes")
    suspend fun createTake(@Body take: Take): Take

    @PUT("api/timeformed/takes/{id}")
    suspend fun updateTake(@Path("id") id: Long, @Body take: Take): Take

    @DELETE("api/timeformed/takes/{id}")
    suspend fun deleteTake(@Path("id") id: Long)
}
