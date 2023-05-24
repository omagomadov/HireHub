package g54516.hirehub.database.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import g54516.hirehub.database.dto.DeveloperDto
import kotlinx.coroutines.tasks.await

class DeveloperRepository {

    fun addDeveloper(entity: DeveloperDto) {
        try {
            Firebase.firestore.collection("Developer")
                .document(entity.email)
                .set(
                    hashMapOf(
                        "email" to entity.email,
                        "firstName" to entity.firstName,
                        "lastName" to entity.lastName,
                        "domain" to entity.domain,
                        "experience_level" to entity.experience_level,
                        "gender" to entity.gender,
                        "phoneNumber" to entity.phoneNumber,
                        "avatar" to entity.avatar,
                        "rating" to entity.rating
                    )
                )
        } catch (e: Exception) {
            Log.d(
                ContentValues.TAG,
                "Erreur lors de l'insertion d'un utilisateur: ${e.toString()}"
            )
        }
    }

    suspend fun getAllDevelopers(): List<DeveloperDto> {
        var developers = mutableListOf<DeveloperDto>()
        try {
            val snapshot = Firebase.firestore
                .collection("Developer")
                .get()
                .await()
            for (document in snapshot.documents) {
                val developer = document.toObject(DeveloperDto::class.java)
                developer?.let {
                    developers.add(developer)
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Erreur lors de la récupération des développeurs ${e.toString()}")
        }
        return developers
    }

    suspend fun getDevelopersOrderedByRating(): List<DeveloperDto> {
        var developers = mutableListOf<DeveloperDto>()
        try {
            val snapshot = Firebase.firestore.collection("Developer")
                .orderBy("rating", Query.Direction.DESCENDING)
                .get()
                .await()
            for (document in snapshot.documents) {
                val developer = document.toObject(DeveloperDto::class.java)
                developer?.let {
                    developers.add(developer)
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Erreur lors de la récupération des développeurs ${e.toString()}")
        }
        return developers
    }

    suspend fun getDeveloperByEmail(email: String): DeveloperDto? {
        var developer: DeveloperDto? = null
        try {
            Firebase.firestore
                .collection("Developer")
                .document(email)
                .get()
                .addOnSuccessListener { result ->
                    developer = result.toObject(DeveloperDto::class.java)
                }.await()
        } catch (e: Exception) {
            Log.d(
                ContentValues.TAG,
                "Erreur lors de la récupération d'un développeur: ${e.toString()}"
            )
        }
        return developer
    }

}