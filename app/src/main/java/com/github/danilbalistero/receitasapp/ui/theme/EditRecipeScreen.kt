package com.github.danilbalistero.receitasapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.danilbalistero.receitasapp.data.Receita
import com.github.danilbalistero.receitasapp.viewmodel.ReceitaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipeScreen(
    receita: Receita,
    viewModel: ReceitaViewModel,
    onRecipeUpdated: () -> Unit,
    onRecipeDeleted: () -> Unit
) {
    var titulo by remember { mutableStateOf(receita.titulo) }
    var ingredientes by remember { mutableStateOf(receita.ingredientes) }
    var modoPreparo by remember { mutableStateOf(receita.modoPreparo) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar Receita") })
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            val receitaAtualizada = receita.copy(
                                titulo = titulo,
                                ingredientes = ingredientes,
                                modoPreparo = modoPreparo
                            )
                            viewModel.atualizarReceita(receitaAtualizada)
                            onRecipeUpdated()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text("Salvar")
                    }
                    OutlinedButton(
                        onClick = {
                            viewModel.excluirReceita(receita)
                            onRecipeDeleted()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text("Excluir")
                    }
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ingredientes,
                onValueChange = { ingredientes = it },
                label = { Text("Ingredientes") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = modoPreparo,
                onValueChange = { modoPreparo = it },
                label = { Text("Modo de Preparo") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
