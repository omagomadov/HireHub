package g54516.hirehub.database.dto

data class AppointmentDto(
    val user_email: String = "",
    val developer_email: String = "",
    val developer_full_name: String = "",
    val developer_avatar: String = "",
    val date: Long = 0L,
)