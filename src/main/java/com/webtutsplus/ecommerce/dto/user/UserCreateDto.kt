package com.webtutsplus.ecommerce.dto.user

import com.webtutsplus.ecommerce.enums.Role

data class UserCreateDto (
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role,
    val password: String,
)