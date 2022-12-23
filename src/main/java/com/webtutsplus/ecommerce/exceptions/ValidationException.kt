package com.webtutsplus.ecommerce.exceptions

class ValidationException : RuntimeException {
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}

    companion object {
        private const val serialVersionUID = 6064663768170825752L
    }
}