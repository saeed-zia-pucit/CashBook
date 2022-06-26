package com.example.cashbook.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cashbook.models.CustomerDetailsEntity
import com.example.cashbook.models.CustomerEntity
import com.example.cashbook.models.VehicleEntity


@Database(entities = [CustomerEntity::class,CustomerDetailsEntity::class,VehicleEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomerDao

}