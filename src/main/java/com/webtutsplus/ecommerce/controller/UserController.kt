package com.webtutsplus.ecommerce.controller

import com.webtutsplus.ecommerce.dto.ResponseDto
import com.webtutsplus.ecommerce.dto.user.SignInDto
import com.webtutsplus.ecommerce.dto.user.SignInResponseDto
import com.webtutsplus.ecommerce.dto.user.SignupDto
import com.webtutsplus.ecommerce.exceptions.AuthenticationFailException
import com.webtutsplus.ecommerce.exceptions.CustomException
import com.webtutsplus.ecommerce.model.User
import com.webtutsplus.ecommerce.repository.UserRepository
import com.webtutsplus.ecommerce.service.AuthenticationService
import com.webtutsplus.ecommerce.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping("user")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
class UserController {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var authenticationService: AuthenticationService

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/all")
    @Throws(AuthenticationFailException::class)
    fun findAllUser(@RequestParam("token") token: String): List<User> {
        authenticationService!!.authenticate(token)
        return userRepository!!.findAll()
    }

    @PostMapping("/signup")
    @Throws(CustomException::class)
    fun Signup(@RequestBody signupDto: SignupDto): ResponseDto {
        return userService!!.signUp(signupDto)
    }

    //TODO token should be updated
    @PostMapping("/signIn")
    @Throws(CustomException::class)
    fun Signup(@RequestBody signInDto: SignInDto): SignInResponseDto {
        return userService!!.signIn(signInDto)
    } //    @PostMapping("/updateUser")
    //    public ResponseDto updateUser(@RequestParam("token") String token, @RequestBody UserUpdateDto userUpdateDto) {
    //        authenticationService.authenticate(token);
    //        return userService.updateUser(token, userUpdateDto);
    //    }
    //    @PostMapping("/createUser")
    //    public ResponseDto updateUser(@RequestParam("token") String token, @RequestBody UserCreateDto userCreateDto)
    //            throws CustomException, AuthenticationFailException {
    //        authenticationService.authenticate(token);
    //        return userService.createUser(token, userCreateDto);
    //    }
}