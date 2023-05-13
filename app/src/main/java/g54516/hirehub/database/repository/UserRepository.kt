package g54516.hirehub.database.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import g54516.hirehub.database.dto.UserDto
import kotlinx.coroutines.tasks.await

class UserRepository : Repository<UserDto> {

    override suspend fun get(id: String): UserDto? {
        var user = UserDto()
        Firebase.firestore
            .collection("User")
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    user = result.toObjects<UserDto>()[0]
                }
            }.await()
        return user
    }

    override suspend fun add(entity: UserDto) {
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

    override suspend fun update(entity: UserDto) {
        TODO("Not yet implemented")
    }

    override suspend fun remove(entity: UserDto) {
        TODO("Not yet implemented")
    }

}