package g54516.hirehub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import g54516.hirehub.database.dao.UserDao
import g54516.hirehub.database.entity.User

/**
 * A Room database that stores [User] objects.
 *
 * @constructor Creates a new instance of [HireHubDB].
 * @property userDao The DAO instance that provides access to the [User] table in the [HireHubDB] database.
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class HireHubDB : RoomDatabase() {

    abstract val userDao: UserDao

    // Companion object is like 'static method' in Java
    // Can access properties without making an instance of the object
    companion object {

        @Volatile
        private var INSTANCE: HireHubDB? = null

        /**
         * Returns a singleton instance of the [HireHubDB] database.
         *
         * @param context The context used to get the application context for creating the database.
         * @return A singleton instance of the [HireHubDB] database.
         */
        fun getInstance(context: Context): HireHubDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
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