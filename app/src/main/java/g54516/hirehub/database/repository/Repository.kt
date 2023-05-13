package g54516.hirehub.database.repository

import g54516.hirehub.database.dto.UserDto

interface Repository<T> {

    suspend fun get(id: String): UserDto?

    suspend fun add(entity: T)

    suspend fun update(entity: T)

    suspend fun remove(entity: T)

}