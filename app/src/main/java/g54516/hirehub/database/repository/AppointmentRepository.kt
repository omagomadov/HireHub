package g54516.hirehub.database.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class AppointmentRepository {

    fun addAppointment(user_email: String, developer_email: String, date: Date) {
        Firebase.firestore.collection("Appointment")
            .add(
                hashMapOf(
                    "user_email" to user_email,
                    "developer_email" to developer_email,
                    "date" to date.time
                )
            )
    }

}