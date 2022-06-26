package com.example.cashbook.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_table")
data class VehicleEntity(

    @ColumnInfo(name = "vehicleNumber") var vehicleNumber: String?,
    @ColumnInfo(name = "driverName") var driverName: String?,
    @ColumnInfo(name = "buyPrice") var buyPrice: String?,
    @ColumnInfo(name = "salePrice") var salePrice: String?,
    @ColumnInfo(name = "buyName") var buyName: String?,
    @ColumnInfo(name = "saleName") var saleName: String?,
    @ColumnInfo(name = "paymentStatus") var paymentStatus: String?){
    @PrimaryKey(autoGenerate = true)
    var customerId: Int = 0
}