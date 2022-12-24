package com.webtutsplus.ecommerce.dto.user

import com.webtutsplus.ecommerce.enums.Role

data class UserUpdateDto (
    // skipping updating passord as of now
    val id: Int,
    val firstName: String,
    val lastName: String,
    val role: Role,
)