package g54516.hirehub.database.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import g54516.hirehub.database.dto.AppointmentDto
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.database.dto.UserDto
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

class AppointmentRepository {

    fun addAppointment(user: UserDto, developer: DeveloperDto, date: LocalDateTime) {
        try {
            Firebase.firestore.collection("Appointment")
                .add(
                    hashMapOf(
                        "user_email" to user.email,
                        "developer_email" to developer.email,
                        "developer_full_name" to developer.firstName + " " + developer.lastName,
                        "user_full_name" to user.firstName + " " + user.lastName,
                        "developer_avatar" to developer.avatar,
                        "pending" to 0,
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

    suspend fun getAppointmentId(appointment: AppointmentDto): String {
        var documentId: String = ""
        try {
            Firebase.firestore
                .collection("Appointment")
                .whereEqualTo("user_email", appointment.user_email)
                .whereEqualTo("developer_email", appointment.developer_email)
                .whereEqualTo("date", appointment.date)
                .get()
                .addOnCompleteListener { snapshot ->
                    if (snapshot.isSuccessful) {
                        val documentSnapshot = snapshot.result.documents[0]
                        documentId = documentSnapshot.id
                    }
                }.await()
        } catch (e: Exception) {
            Log.d(
                ContentValues.TAG,
                "Erreur lors de la récupération de l'ID du rendez-vous: ${e.toString()}"
            )
        }
        return documentId
    }

    fun approuveAppointment(appointmentId: String, appointment: AppointmentDto) {
        try {
            Firebase.firestore
                .collection("Appointment")
                .document(appointmentId)
                .update("pending", 1)
        } catch (e: Exception) {
            Log.d(
                ContentValues.TAG,
                "Erreur lors de l'approbation du rendez-vous: ${e.toString()}"
            )
        }
    }

    fun disapprouveAppointment(appointmentId: String, appointment: AppointmentDto) {
        try {
            Firebase.firestore
                .collection("Appointment")
                .document(appointmentId)
                .update("pending", 3)
        } catch (e: Exception) {
            Log.d(
                ContentValues.TAG,
                "Erreur lors de la désapprobation d'un rendez-vous: ${e.toString()}"
            )
        }
    }

    suspend fun getAllPendingAppointments(developer_email: String?): List<AppointmentDto> {
        var appointments = mutableListOf<AppointmentDto>()
        try {
            Firebase.firestore
                .collection("Appointment")
                .whereEqualTo("developer_email", developer_email)
                .whereEqualTo("pending", 0)
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

    suspend fun getUserPassedAppointments(user_email: String?): List<AppointmentDto> {
        var appointments = mutableListOf<AppointmentDto>()
        try {
            Firebase.firestore
                .collection("Appointment")
                .whereEqualTo("user_email", user_email)
                .whereEqualTo("pending", 1)
                .whereLessThan(
                    "date", System.currentTimeMillis()
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
                "Erreur lors de la récupération des rendez-vous passés:  ${e.toString()}"
            )
        }
        return appointments
    }

    suspend fun getUserIncomeAppointments(user_email: String?): List<AppointmentDto> {
        var appointments = mutableListOf<AppointmentDto>()
        try {
            Firebase.firestore
                .collection("Appointment")
                .whereEqualTo("user_email", user_email)
                .whereEqualTo("pending", 1)
                .whereGreaterThan(
                    "date", System.currentTimeMillis()
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
                "Erreur lors de la récupération des rendez-vous à venir : ${e.toString()}"
            )
        }
        return appointments
    }

    suspend fun getDeveloperIncomeAppointments(developer_email: String?): List<AppointmentDto> {
        Log.d(
            "getDeveloper", LocalDateTime.now()
                .toInstant(ZoneOffset.UTC).toEpochMilli().toString()
        )
        var appointments = mutableListOf<AppointmentDto>()
        try {
            Firebase.firestore
                .collection("Appointment")
                .whereEqualTo("developer_email", developer_email)
                .whereEqualTo("pending", 1)
                .whereGreaterThan(
                    "date", System.currentTimeMillis()
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
                "Erreur lors de la récupération des rendez-vous à venir: ${e.toString()}"
            )
        }
        return appointments
    }

    suspend fun getDeveloperPassedAppointments(developer_email: String?): List<AppointmentDto> {
        var appointments = mutableListOf<AppointmentDto>()
        try {
            Firebase.firestore
                .collection("Appointment")
                .whereEqualTo("developer_email", developer_email)
                .whereEqualTo("pending", 1)
                .whereLessThan(
                    "date", System.currentTimeMillis()
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
                "Erreur lors de la récupération des rendez-vous passés:  ${e.toString()}"
            )
        }
        return appointments
    }

}