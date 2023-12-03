package com.example.carsharing.entities

import java.io.Serializable

data class Car(
    val mark: String,
    var imageUrl: String,
    val location: List<String>,
    val status: Boolean = true,
) : Serializable {
    var id: String = ""

    constructor(
        id: String,
        mark: String,
        imageUrl: String,
        location: List<String>,
        status: Boolean = true,
    ) : this(mark, imageUrl, location, status) {
        this.id = id
    }
}
