package com.innovatube.boilerplate.domain.model

data class LoginSession(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
    val refreshToken: String,
    val createdAt: Int
)
