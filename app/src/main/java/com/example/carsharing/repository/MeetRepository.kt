package com.example.carsharing.repository

import android.content.Context
import com.example.carsharing.data.DatabaseHandler

interface MeetRepository {
    suspend fun regist(login:String, password:String, context: Context):Boolean

    suspend fun auth(login:String, password:String, context: Context):Boolean

}