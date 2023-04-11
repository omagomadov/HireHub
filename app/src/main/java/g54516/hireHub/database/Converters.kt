package g54516.hireHub.database

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value : Long?) : Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(value : Date?) : Long? {
        return value?.time?.toLong()
    }

}