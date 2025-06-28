package com.github.danilbalistero.receitasapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.danilbalistero.receitasapp.data.Receita
import com.github.danilbalistero.receitasapp.data.ReceitaDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReceitaViewModel(private val dao: ReceitaDao) : ViewModel() {

    val receitas: StateFlow<List<Receita>> = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun adicionarReceita(receita: Receita) {
        viewModelScope.launch {
            dao.insert(receita)
        }
    }

    fun atualizarReceita(receita: Receita) {
        viewModelScope.launch {
            dao.update(receita)
        }
    }

    fun excluirReceita(receita: Receita) {
        viewModelScope.launch {
            dao.delete(receita)
        }
    }
}

class ReceitaViewModelFactory(private val dao: ReceitaDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReceitaViewModel(dao) as T
    }
}
