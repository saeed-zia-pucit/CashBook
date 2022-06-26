package com.example.cashbook.database

import com.example.cashbook.models.CustomerDetailsEntity
import com.example.cashbook.models.CustomerEntity
import com.example.cashbook.models.VehicleEntity

interface DbHelper {

    suspend fun getUsers(): List<CustomerEntity>
    suspend fun insertAll(users: List<CustomerEntity>)
    suspend fun insert(contactEntity: CustomerEntity)

    suspend fun getAllDetails(phone:String): List<CustomerDetailsEntity>
   // suspend fun insertAllDetails(users: List<CustomerDetailsEntity>)
    suspend fun insert(contactEntity: CustomerDetailsEntity)
    suspend fun insert(vehicleEntity: VehicleEntity)

    suspend fun update(vehicleEntity: VehicleEntity)
    suspend fun getAllVehicles(): List<VehicleEntity>

}