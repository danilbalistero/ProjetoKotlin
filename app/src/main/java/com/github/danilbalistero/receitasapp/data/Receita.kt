package com.github.danilbalistero.receitasapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receitas")
data class Receita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val ingredientes: String,
    val modoPreparo: String,
    val imagemUri: String? = null,
    val tempoPreparo: String = ""
)