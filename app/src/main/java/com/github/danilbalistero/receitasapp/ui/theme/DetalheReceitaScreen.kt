package com.github.danilbalistero.receitasapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.danilbalistero.receitasapp.data.Receita

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheReceitaScreen(
    receita: Receita,
    onEditarClick: (Receita) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detalhes da Receita") })
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    onClick = { onEditarClick(receita) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Editar Receita")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = receita.titulo, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Ingredientes:", style = MaterialTheme.typography.titleMedium)
            Text(text = receita.ingredientes)
            Text(text = "Modo de Preparo:", style = MaterialTheme.typography.titleMedium)
            Text(text = receita.modoPreparo)
        }
    }
}
