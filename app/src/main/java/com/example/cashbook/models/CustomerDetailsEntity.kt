package com.example.cashbook.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "customer_details_table")
data class CustomerDetailsEntity(
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String,
    @ColumnInfo(name = "truckNumber") var truckNumber: String,
    @ColumnInfo(name = "getGiveStatus") var getGiveStatus: String?,
    @ColumnInfo(name = "date") var date: String?,
    @ColumnInfo(name = "balAmount") var balAmount: Int?,
    @ColumnInfo(name = "amountGive") var amountGive:Int?,
    @ColumnInfo(name = "amountGot") var amountGot: Int?,): Serializable{
    @PrimaryKey(autoGenerate = true)
    var customerId: Int = 0
    }
