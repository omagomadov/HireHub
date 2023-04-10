package g54516.hireHub.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class User (

    @PrimaryKey(autoGenerate = true)
    val userId : Long?,

    @ColumnInfo(name = "mail_address")
    val mailAddress : String?,

    @ColumnInfo(name = "registration_time")
    val registrationTime : LocalDateTime?

    )