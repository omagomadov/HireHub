package g54516.hireHub.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User (

    @PrimaryKey(autoGenerate = true)
    val userId : Long? = 0L,

    @ColumnInfo(name = "mail_address")
    val mailAddress : String?,

    @ColumnInfo(name = "registration_time")
    val registrationTime : Date?

    )