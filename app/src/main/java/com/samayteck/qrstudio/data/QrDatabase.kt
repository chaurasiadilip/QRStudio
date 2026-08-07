package com.samayteck.qrstudio.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QrHistoryDao {
    @Query("SELECT * FROM qr_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<QrHistoryEntity>>

    @Query("SELECT * FROM qr_history WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavorites(): Flow<List<QrHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: QrHistoryEntity)

    @Update
    suspend fun update(entity: QrHistoryEntity)

    @Delete
    suspend fun delete(entity: QrHistoryEntity)

    @Query("DELETE FROM qr_history")
    suspend fun deleteAll()
}

@Database(entities = [QrHistoryEntity::class], version = 2, exportSchema = false)
@TypeConverters(QrTypeConverters::class)
abstract class QrDatabase : RoomDatabase() {
    abstract fun qrHistoryDao(): QrHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: QrDatabase? = null

        fun getDatabase(context: Context): QrDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QrDatabase::class.java,
                    "qr_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
