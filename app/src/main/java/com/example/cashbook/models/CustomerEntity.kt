package com.example.cashbook.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "customer_table")
data class CustomerEntity(

    @ColumnInfo(name = "name") var name: String?,
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String,
    @ColumnInfo(name = "balAmount") var balAmount: Int?,
    @ColumnInfo(name = "getGiveStatus") var getGiveStatus: String?): Serializable
