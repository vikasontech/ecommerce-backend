package com.webtutsplus.ecommerce.repository

import com.webtutsplus.ecommerce.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    override fun findAll(): List<User>
    fun findByEmail(email: String?): User
    fun findUserByEmail(email: String?): User
}