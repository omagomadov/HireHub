package g54516.hireHub.database

import androidx.room.Database
import g54516.hireHub.database.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class HireHubDB {
    //todo
}