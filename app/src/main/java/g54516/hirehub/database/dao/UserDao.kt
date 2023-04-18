package g54516.hirehub.database.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import g54516.hirehub.database.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM User WHERE mail_address = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM User")
    suspend fun getAllUsers(): List<User>

    // You don't need to specify 'suspend' because creating a LiveData instance is
    // a non-blocking action that runs in the background and provides an asynchronous data stream
    // that can be observed by the UI.
    @Query("SELECT mail_address FROM User")
    fun getAllEmails(): LiveData<List<String>>

    @Query("DELETE FROM User")
    suspend fun clear()

}