package <%= package_name %>.data.repository.datasource.local

import android.arch.persistence.room.*
import <%= package_name %>.data.api.home.entity.HeaderEntity
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
