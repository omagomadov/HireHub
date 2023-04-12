package g54516.hirehub.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import g54516.hirehub.database.entity.User

/**
 * Data Access Object (DAO) interface for accessing and modifying [User] data in the HireHub database.
 */
@Dao
interface UserDao {

    /**
     * Inserts a new [User] into the database.
     *
     * @param user the [User] to insert
     */
    @Insert
    suspend fun insert(user: User)

    /**
     * Updates a existing [User] in the database.
     *
     * @param user the [User] to update
     */
    @Update
    suspend fun update(user: User)

    /**
     * Retrieves a [User] with the given email in the database.
     *
     * @param email the email of the [User]
     * @return the [User] with the given email, or null if no such user exists
     */
    @Query("SELECT * FROM User WHERE mail_address = :email")
    suspend fun getUserByEmail(email: String): User?

    /**
     * Retrieves all [User] in the database.
     * @return the list of [User]
     */
    @Query("SELECT * FROM User")
    suspend fun getAllUsers(): List<User>

    /**
     * Removes all [User] from the table.
     */
    @Query("DELETE FROM User")
    suspend fun clear()

}