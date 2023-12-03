package com.example.carsharing.repositoryImpl

import android.content.Context
import com.example.carsharing.data.DatabaseHandler
import com.example.carsharing.repository.MeetRepository

class MeetRepositoryImpl : MeetRepository {
    override suspend fun regist(login: String, password: String, context: Context): Boolean {
        return DatabaseHandler().registration(login, password, context)
    }

    override suspend fun auth(login: String, password: String, context: Context): Boolean {
        return DatabaseHandler().auth(login, password, context)
    }
}