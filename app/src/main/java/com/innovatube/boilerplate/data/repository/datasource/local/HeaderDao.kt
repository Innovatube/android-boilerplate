package com.innovatube.boilerplate.data.repository.datasource.local

import android.arch.persistence.room.*
import com.innovatube.boilerplate.data.api.home.entity.HeaderEntity
import io.reactivex.Flowable

@Dao
interface HeaderDao {
    @get:Query("SELECT * FROM HeaderEntity")
    val all: Flowable<HeaderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(headerEntity: HeaderEntity)

    @Query("DELETE FROM HeaderEntity")
    fun deleteAll()

    @Transaction
    fun updateData(headerEntity: HeaderEntity) {
        deleteAll()
        insert(headerEntity)
    }

    @Delete
    fun delete(headerEntity: HeaderEntity)
}
