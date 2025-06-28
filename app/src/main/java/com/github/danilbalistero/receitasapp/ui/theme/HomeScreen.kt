package com.github.danilbalistero.receitasapp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.danilbalistero.receitasapp.data.Receita
import com.github.danilbalistero.receitasapp.viewmodel.ReceitaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(
    viewModel: ReceitaViewModel,
    onNovaReceitaClick: () -> Unit,
    onEditarReceitaClick: (Receita) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Minhas Receitas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNovaReceitaClick) {
                Text("+")
            }
        }
    ) { padding ->
        val receitas by viewModel.receitas.collectAsState()

        if (receitas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("Nenhuma receita cadastrada.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(receitas) { receita ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onEditarReceitaClick(receita) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = receita.titulo, style = MaterialTheme.typography.titleLarge)
                            Text(text = receita.ingredientes, maxLines = 1)
                        }
                    }
                }

            }
        }
    }
}


