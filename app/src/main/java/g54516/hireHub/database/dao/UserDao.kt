package g54516.hireHub.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import g54516.hireHub.database.entity.User

@Dao
interface UserDao {

    @Insert
    fun insert(user : User)

    @Update
    fun update(user : User)

}