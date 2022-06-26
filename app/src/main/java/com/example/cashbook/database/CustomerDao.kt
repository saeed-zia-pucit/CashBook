package com.example.cashbook.database

import androidx.room.*
import com.example.cashbook.models.CustomerDetailsEntity
import com.example.cashbook.models.CustomerEntity
import com.example.cashbook.models.VehicleEntity

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customer_table")
    fun getAll(): List<CustomerEntity>

    @Query("SELECT * FROM customer_details_table WHERE phoneNumber LIKE :phone")
    fun getAll(phone:String): List<CustomerDetailsEntity>

    @Insert
    fun insertAll( contacts: List<CustomerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert( contactEntity: CustomerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert( CustomerDetailsEntity: CustomerDetailsEntity)

    @Delete
    fun delete(todo: CustomerEntity)

    @Delete
    fun delete(todo: CustomerDetailsEntity)
    @Update
    fun updateTodo( todos: CustomerEntity)

    @Update
    fun updateTodo( todos: CustomerDetailsEntity)
    //


    @Query("SELECT * FROM vehicle_table")
    fun getAllVehicles(): List<VehicleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert( vehicleEntity: VehicleEntity)

    @Delete
    fun delete(vehicleEntity: VehicleEntity)


    @Update
    fun update( vehicleEntity: VehicleEntity)


}