package com.webtutsplus.ecommerce.service

import com.webtutsplus.ecommerce.config.MessageStrings
import com.webtutsplus.ecommerce.dto.ResponseDto
import com.webtutsplus.ecommerce.dto.user.SignInDto
import com.webtutsplus.ecommerce.dto.user.SignInResponseDto
import com.webtutsplus.ecommerce.dto.user.SignupDto
import com.webtutsplus.ecommerce.dto.user.UserCreateDto
import com.webtutsplus.ecommerce.enums.ResponseStatus
import com.webtutsplus.ecommerce.enums.Role
import com.webtutsplus.ecommerce.exceptions.AuthenticationFailException
import com.webtutsplus.ecommerce.exceptions.CustomException
import com.webtutsplus.ecommerce.model.AuthenticationToken
import com.webtutsplus.ecommerce.model.User
import com.webtutsplus.ecommerce.repository.UserRepository
import com.webtutsplus.ecommerce.utils.Helper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.time.LocalDateTime
import java.util.*
import javax.xml.bind.DatatypeConverter

@Service
class UserService {
    @Autowired
    var userRepository: UserRepository? = null

    @Autowired
    var authenticationService: AuthenticationService? = null
    var logger = LoggerFactory.getLogger(UserService::class.java)

    @Throws(CustomException::class)
    fun signUp(signupDto: SignupDto): ResponseDto {
        // Check to see if the current email address has already been registered.
        if (Helper.notNull(userRepository!!.findByEmail(signupDto.email))) {
            // If the email address has been registered then throw an exception.
            throw CustomException("User already exists")
        }
        // first encrypt the password
        var encryptedPassword = signupDto.password
        try {
            encryptedPassword = hashPassword(signupDto.password)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            logger.error("hashing password failed {}", e.message)
        }
//        val user = User(signupDto.firstName, signupDto.lastName, signupDto.email, Role.user, encryptedPassword)
        val user = User(
            firstName = signupDto.firstName,
            lastName = signupDto.lastName,
            email = signupDto.email,
            role = Role.user,
            password = encryptedPassword
        )
        val createdUser: User
        return try {
            // save the User
            createdUser = userRepository!!.save(user)
            // generate token for user
            val authenticationToken = AuthenticationToken(createdUser)
            // save token in database
            authenticationService!!.saveConfirmationToken(authenticationToken)
            // success in creating
            ResponseDto(ResponseStatus.success.toString(), MessageStrings.USER_CREATED)
        } catch (e: Exception) {
            // handle signup error
            throw CustomException(e.message)
        }
    }

    @Throws(CustomException::class)
    fun signIn(signInDto: SignInDto): SignInResponseDto {
        // first find User by email
        val user = userRepository!!.findByEmail(signInDto.email)
        if (!Helper.notNull(user)) {
            throw AuthenticationFailException("user not present")
        }
        try {
            // check if password is right
            if (user!!.password != hashPassword(signInDto.password)) {
                // passowrd doesnot match
                throw AuthenticationFailException(MessageStrings.WRONG_PASSWORD)
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            logger.error("hashing password failed {}", e.message)
            throw CustomException(e.message)
        }
        val token = authenticationService!!.getToken(user)
        if (!Helper.notNull(token)) {
            // token not present
            throw CustomException("token not present")
        }
        return SignInResponseDto("success", token!!.token)
    }

    @Throws(NoSuchAlgorithmException::class)
    fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("MD5")
        md.update(password.toByteArray())
        val digest = md.digest()
        return DatatypeConverter
            .printHexBinary(digest).uppercase(Locale.getDefault())
    }

    @Throws(CustomException::class, AuthenticationFailException::class)
    fun createUser(token: String, userCreateDto: UserCreateDto): ResponseDto {
        val creatingUser = authenticationService!!.getUser(token)
        if (!canCrudUser(creatingUser!!.role)) {
            // user can't create new user
            throw AuthenticationFailException(MessageStrings.USER_NOT_PERMITTED)
        }
        var encryptedPassword = userCreateDto.password
        try {
            encryptedPassword = hashPassword(userCreateDto.password)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            logger.error("hashing password failed {}", e.message)
        }
        val user = User(
           firstName = userCreateDto.firstName,
            lastName = userCreateDto.lastName,
            email= userCreateDto.email,
            role = userCreateDto.role,
            password = encryptedPassword
        )
        val createdUser: User
        return try {
            createdUser = userRepository!!.save(user)
            val authenticationToken = AuthenticationToken(
               user = createdUser,
                createdDate = LocalDateTime.now(),
                token = UUID.randomUUID().toString()
            )
            authenticationService!!.saveConfirmationToken(authenticationToken)
            ResponseDto(ResponseStatus.success.toString(), MessageStrings.USER_CREATED)
        } catch (e: Exception) {
            // handle user creation fail error
            throw CustomException(e.message)
        }
    }

    fun canCrudUser(role: Role): Boolean {
        return if (role === Role.admin || role === Role.manager) {
            true
        } else false
    }

    fun canCrudUser(userUpdating: User, userIdBeingUpdated: Int): Boolean {
        val role = userUpdating.role
        // admin and manager can crud any user
        if (role === Role.admin || role === Role.manager) {
            return true
        }
        // user can update his own record, but not his role
        return if (role === Role.user && userUpdating.id == userIdBeingUpdated) {
            true
        } else false
    }
}