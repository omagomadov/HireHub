package g54516.hirehub.database.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import g54516.hirehub.database.dto.AppointmentDto
import g54516.hirehub.database.dto.DeveloperDto
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

class AppointmentRepository {

    fun addAppointment(user_email: String, developer: DeveloperDto, date: LocalDateTime) {
        try {
            Firebase.firestore.collection("Appointment")
                .add(
                    hashMapOf(
                        "user_email" to user_email,
                        "developer_email" to developer.email,
                        "developer_full_name" to developer.firstName + " " + developer.lastName,
                        "developer_avatar" to developer.avatar,
                        "date" to ZonedDateTime
                            .of(date, ZoneId.systemDefault()).toInstant().toEpochMilli()
                        // Convert the given date and time to a timestamp in milliseconds
                        // using the system default time zone
                    )
                )
        } catch (e: Exception) {
            Log.d(
                ContentValues.TAG,
                "Erreur lors de l'insertion d'un rendez-vous:  ${e.toString()}"
            )
        }
    }

    suspend fun getPassedAppointments(user_email: String?): List<AppointmentDto> {
        var appointments = mutableListOf<AppointmentDto>()
        try {
            Firebase.firestore
                .collection("Appointment")
                .whereEqualTo("user_email", user_email)
                .whereLessThan(
                    "date", LocalDateTime.now()
                        .toInstant(ZoneOffset.UTC).toEpochMilli()
                )
                .get()
                .addOnCompleteListener { snapshot ->
                    if (snapshot.isSuccessful) {
                        for (document in snapshot.result.documents) {
                            var appointment = document.toObject(AppointmentDto::class.java)
                            if (appointment != null) {
                                appointments.add(appointment)
                            }
                        }
                    }
                }.await()
        } catch (e: Exception) {
            Log.d(
                ContentValues.TAG,
                "Erreur lors de la récupération des rendez-vous à venir:  ${e.toString()}"
            )
        }
        return appointments
    }

    suspend fun getIncomeAppointments(user_email: String?): List<AppointmentDto> {
        var appointments = mutableListOf<AppointmentDto>()
        try {
            Firebase.firestore
                .collection("Appointment")
                .whereEqualTo("user_email", user_email)
                .whereGreaterThan(
                    "date", LocalDateTime.now()
                        .toInstant(ZoneOffset.UTC).toEpochMilli()
                )
                .get()
                .addOnCompleteListener { snapshot ->
                    if (snapshot.isSuccessful) {
                        for (document in snapshot.result.documents) {
                            var appointment = document.toObject(AppointmentDto::class.java)
                            if (appointment != null) {
                                appointments.add(appointment)
                            }
                        }
                    }
                }.await()
        } catch (e: Exception) {
            Log.d(
                ContentValues.TAG,
                "Erreur lors de la récupération des rendez-vous passés : ${e.toString()}"
            )
        }
        return appointments
    }

}