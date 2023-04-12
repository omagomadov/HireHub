package g54516.hirehub.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Represents a user of the HireHub application.
 *
 * @property userId the unique ID of the user in the database (auto-generated)
 * @property email the email address of the user
 * @property date the registration time of the user
 */
@Entity(tableName = "User")
data class User(

    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,

    @ColumnInfo(name = "mail_address")
    val email: String?,

    @ColumnInfo(name = "registration_time")
    val date: Date?

)