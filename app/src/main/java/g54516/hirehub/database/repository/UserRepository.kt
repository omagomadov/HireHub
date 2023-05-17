package g54516.hirehub.database.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import g54516.hirehub.database.dto.UserDto
import kotlinx.coroutines.tasks.await

class UserRepository {

    suspend fun getUserByEmail(email: String): UserDto? {
        var user: UserDto? = null
        Firebase.firestore
            .collection("User")
            .document(email)
            .get()
            .addOnSuccessListener { result ->
                user = result.toObject(UserDto::class.java)
            }.await()
        return user
    }

    fun add(entity: UserDto) {
        Firebase.firestore.collection("User")
            .document(entity.email)
            .set(
                hashMapOf(
                    "email" to entity.email,
                    "firstName" to entity.firstName,
                    "lastName" to entity.lastName,
                    "gender" to entity.gender,
                    "phoneNumber" to entity.phoneNumber
                )
            )
    }

}