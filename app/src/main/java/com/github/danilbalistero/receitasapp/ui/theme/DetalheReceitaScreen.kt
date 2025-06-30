package com.github.danilbalistero.receitasapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.github.danilbalistero.receitasapp.data.Receita

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheReceitaScreen(
    receita: Receita,
    navController: NavController,
    onEditarClick: (Receita) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Receita") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
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
            receita.imagemUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
            }

            Text(text = receita.titulo, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Ingredientes:", style = MaterialTheme.typography.titleMedium)
            Text(text = receita.ingredientes)
            Text(text = "Modo de Preparo:", style = MaterialTheme.typography.titleMedium)
            Text(text = receita.modoPreparo)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onEditarClick(receita) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Editar Receita")
            }
        }
    }
}