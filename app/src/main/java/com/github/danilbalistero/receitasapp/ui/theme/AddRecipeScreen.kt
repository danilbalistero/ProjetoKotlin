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
fun AddRecipeScreen(
    viewModel: ReceitaViewModel,
    onRecipeSaved: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var ingredientes by remember { mutableStateOf("") }
    var modoPreparo by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nova Receita") })
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    onClick = {
                        if (titulo.isNotBlank()) {
                            val novaReceita = Receita(
                                titulo = titulo,
                                ingredientes = ingredientes,
                                modoPreparo = modoPreparo
                            )
                            viewModel.adicionarReceita(novaReceita)
                            onRecipeSaved()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Salvar Receita")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
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
