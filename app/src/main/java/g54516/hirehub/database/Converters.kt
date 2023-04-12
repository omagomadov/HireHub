package g54516.hirehub.database

import androidx.room.TypeConverter
import java.util.*

/**
 * Provides conversion methods between Date objects and Long values,
 * so that they can be stored in the database using Room.
 */
class Converters {

    /**
     * Converts a Long value representing a timestamp to a Date object.
     *
     * @param value the timestamp as a Long value, or null if the value is null in the database.
     * @return the corresponding Date object, or null if the value is null.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    /**
     * Converts a Date object to a Long value representing a timestamp.
     *
     * @param value the Date object, or null if the value is null in the database.
     * @return the corresponding timestamp as a Long value, or null if the value is null.
     */
    @TypeConverter
    fun dateToTimestamp(value: Date?): Long? {
        return value?.time?.toLong()
    }

}