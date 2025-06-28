package com.github.danilbalistero.receitasapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceitaDao {
    @Insert
    suspend fun insert(receita: Receita)

    @Query("SELECT * FROM receitas ORDER BY id DESC")
    fun getAll(): Flow<List<Receita>>

    @Update
    suspend fun update(receita: Receita)

    @Delete
    suspend fun delete(receita: Receita)
}
