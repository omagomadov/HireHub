package g54516.hireHub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import g54516.hireHub.database.dao.UserDao
import g54516.hireHub.database.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class HireHubDB : RoomDatabase() {

    abstract val userDao : UserDao

    companion object {

        @Volatile
        private var INSTANCE : HireHubDB? = null

        fun getInstance(context : Context) : HireHubDB {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HireHubDB::class.java,
                        "HireHubDB"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}