package g54516.hirehub.database.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import g54516.hirehub.database.dto.UserDto
import kotlinx.coroutines.tasks.await

class UserRepository {

    fun addUser(entity: UserDto) {
        try {
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
        } catch (e: Exception) {
            Log.d(
                ContentValues.TAG,
                "Erreur lors de l'insertion d'un utilisateur: ${e.toString()}"
            )
        }
    }

    suspend fun getUserByEmail(email: String): UserDto? {
        var user: UserDto? = null
        try {
            Firebase.firestore
                .collection("User")
                .document(email)
                .get()
                .addOnSuccessListener { result ->
                    user = result.toObject(UserDto::class.java)
                }.await()
        } catch (e: Exception) {
            Log.d(
                ContentValues.TAG,
                "Erreur lors de la récupération d'un utilisateur: ${e.toString()}"
            )
        }
        return user
    }

}