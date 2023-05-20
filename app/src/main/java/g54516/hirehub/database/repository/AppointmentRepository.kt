package g54516.hirehub.database.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import g54516.hirehub.database.dto.AppointmentDto
import g54516.hirehub.database.dto.DeveloperDto
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneOffset

class AppointmentRepository {

    fun addAppointment(user_email: String, developer: DeveloperDto, date: LocalDate) {
        Firebase.firestore.collection("Appointment")
            .add(
                hashMapOf(
                    "user_email" to user_email,
                    "developer_email" to developer.email,
                    "developer_full_name" to developer.firstName + " " + developer.lastName,
                    "developer_avatar" to developer.avatar,
                    "date" to date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
                )
            )
    }

    suspend fun getAppointments(user_email: String?): List<AppointmentDto> {
        var appointments = mutableListOf<AppointmentDto>()
        Firebase.firestore
            .collection("Appointment")
            .get().addOnCompleteListener { snapshot ->
                if (snapshot.isSuccessful) {
                    for (document in snapshot.result.documents) {
                        val appointment = document.toObject(AppointmentDto::class.java)
                        if (appointment != null) {
                            appointments.add(appointment)
                        }
                    }
                }
            }.await()
        return appointments
    }

}