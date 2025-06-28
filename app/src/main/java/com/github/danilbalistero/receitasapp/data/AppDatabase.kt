package com.github.danilbalistero.receitasapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Receita::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun receitaDao(): ReceitaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "receita_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
