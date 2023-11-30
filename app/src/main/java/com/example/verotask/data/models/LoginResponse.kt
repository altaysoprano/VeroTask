package com.example.verotask.data.models

data class LoginResponse(
    val oauth: OAuthData
)

data class OAuthData(
    val access_token: String
)