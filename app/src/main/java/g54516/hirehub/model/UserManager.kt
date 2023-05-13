package g54516.hirehub.model

import g54516.hirehub.database.dto.UserDto

object UserManager {

    private var currentUser: UserDto? = null
    val user: UserDto
        get() = currentUser ?: throw IllegalStateException("User is not initialized")

    fun initialize(user: UserDto?) {
        currentUser = user
    }

}