package com.example.carsharing.entities

import java.io.Serializable

data class Car(
    val id:String,
    val mark:String,
    val imageUrl:String,
    val location:List<String>,
    val status: Boolean
):Serializable
