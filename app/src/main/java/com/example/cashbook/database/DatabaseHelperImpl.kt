package com.example.cashbook.database

import com.example.cashbook.models.CustomerDetailsEntity
import com.example.cashbook.models.CustomerEntity
import com.example.cashbook.models.VehicleEntity


class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DbHelper {

    override suspend fun getUsers(): List<CustomerEntity> = appDatabase.customerDao().getAll()
    override suspend fun insertAll(contacts: List<CustomerEntity>) = appDatabase.customerDao().insertAll(contacts)
    override suspend fun insert(contactEntity: CustomerEntity) = appDatabase.customerDao().insert(contactEntity)

    override suspend fun insert(contactEntity: CustomerDetailsEntity) = appDatabase.customerDao().insert(contactEntity)
    override suspend fun insert(vehicleEntity: VehicleEntity) = appDatabase.customerDao().insert(vehicleEntity)

    override suspend fun getAllDetails(phone:String): List<CustomerDetailsEntity> = appDatabase.customerDao().getAll(phone)
    override suspend fun update(vehicleEntity: VehicleEntity) = appDatabase.customerDao().update(vehicleEntity)

    override suspend fun getAllVehicles(): List<VehicleEntity> =appDatabase.customerDao().getAllVehicles()

    //override suspend fun insertAllDetails(users: List<CustomerDetailsEntity>) =appDatabase.customerDao().insert()

}