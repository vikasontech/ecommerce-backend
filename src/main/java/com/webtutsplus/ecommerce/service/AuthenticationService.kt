package com.webtutsplus.ecommerce.service

import com.webtutsplus.ecommerce.config.MessageStrings
import com.webtutsplus.ecommerce.exceptions.AuthenticationFailException
import com.webtutsplus.ecommerce.model.AuthenticationToken
import com.webtutsplus.ecommerce.model.User
import com.webtutsplus.ecommerce.repository.TokenRepository
import com.webtutsplus.ecommerce.utils.Helper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthenticationService {
    @Autowired
    private lateinit var repository: TokenRepository
    fun saveConfirmationToken(authenticationToken: AuthenticationToken) {
        repository!!.save(authenticationToken)
    }

    fun getToken(user: User): AuthenticationToken {
        return repository!!.findTokenByUser(user)
    }

    //todo: need to fix this method remove nullable resposne
    fun getUser(token: String): User? {
        val authenticationToken = repository!!.findTokenByToken(token)
        if (Helper.notNull(authenticationToken)) {
            if (Helper.notNull(authenticationToken!!.user)) {
                return authenticationToken.user
            }else return null
        }
        else return null
    }

    @Throws(AuthenticationFailException::class)
    fun authenticate(token: String) {
        if (!Helper.notNull(token)) {
            throw AuthenticationFailException(MessageStrings.AUTH_TOEKN_NOT_PRESENT)
        }
        if (!Helper.notNull(getUser(token))) {
            throw AuthenticationFailException(MessageStrings.AUTH_TOEKN_NOT_VALID)
        }
    }
}