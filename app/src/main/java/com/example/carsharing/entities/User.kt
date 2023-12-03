package com.example.carsharing.entities

import java.sql.ClientInfoStatus

data class User(
    val id:String,
    val login:String,
    val password:String,
    val imageUrl:String,
    val status: Boolean
)
