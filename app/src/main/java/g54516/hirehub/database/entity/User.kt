package g54516.hirehub.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "User")
data class User(

    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,

    @ColumnInfo(name = "mail_address")
    val email: String?,

    @ColumnInfo(name = "firstName")
    val firstName: String?,

    @ColumnInfo(name = "lastName")
    val lastName: String?,

    @ColumnInfo(name = "gender")
    val gender: String?,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: Int?,

    @ColumnInfo(name = "registration_time")
    val date: Date?

)