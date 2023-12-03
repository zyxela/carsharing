package com.example.carsharing.entities

data class UserRent(
    val userId:String,
    val carId:String
){
    var id =""

    constructor(
        id:String,
        userId: String,
        carId: String
    ):this(userId, carId)
}
