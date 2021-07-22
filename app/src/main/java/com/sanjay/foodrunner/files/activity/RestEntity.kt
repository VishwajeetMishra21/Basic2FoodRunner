package com.sanjay.foodrunner.files.activity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant")
data class RestEntity (
      @PrimaryKey val rest_id : Int,
      @ColumnInfo(name = "rest_name") val restName : String,
      @ColumnInfo(name = "rest_ratings") val restRatings : String,
      @ColumnInfo(name = "rest_cost") val restCost : String,
      @ColumnInfo(name = "rest_image") val restImage : String
        )