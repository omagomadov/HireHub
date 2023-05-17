package g54516.hirehub.database.repository

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import g54516.hirehub.database.dto.DeveloperDto
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class DeveloperRepository {


    suspend fun getAllDevelopers(): List<DeveloperDto> {
        var developers = mutableListOf<DeveloperDto>()
        try {
            val snapshot = Firebase.firestore.collection("Developer").get().await()
            for(document in snapshot.documents) {
                val developer = document.toObject(DeveloperDto::class.java)
                developer?.let {
                    developers.add(developer)
                }
            }
        } catch (e: Exception) {
            //todo
        }
        return developers
    }

}