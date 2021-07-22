package com.sanjay.foodrunner.files.activity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RestDao {

    @Insert
    fun insertRest(restEntity:RestEntity)

    @Delete
    fun deleteRest(restEntity: RestEntity)

    @Query("SELECT * FROM restaurant")
    fun getAllRest() : List<RestEntity>

    @Query("SELECT * FROM restaurant where rest_id = :restId")
    fun getRestById(restId : String) : RestEntity
    }