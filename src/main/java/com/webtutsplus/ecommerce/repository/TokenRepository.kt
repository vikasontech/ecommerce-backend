package com.webtutsplus.ecommerce.repository

import com.webtutsplus.ecommerce.model.AuthenticationToken
import com.webtutsplus.ecommerce.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<AuthenticationToken?, Int?> {
    fun findTokenByUser(user: User?): AuthenticationToken?
    fun findTokenByToken(token: String?): AuthenticationToken?
}