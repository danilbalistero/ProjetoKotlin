package com.github.danilbalistero.receitasapp.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.github.danilbalistero.receitasapp.data.Receita
import com.github.danilbalistero.receitasapp.viewmodel.ReceitaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipeScreen(
    receita: Receita,
    viewModel: ReceitaViewModel,
    navController: NavController,
    onRecipeUpdated: () -> Unit,
    onRecipeDeleted: () -> Unit
) {
    var titulo by remember { mutableStateOf(receita.titulo) }
    var ingredientes by remember { mutableStateOf(receita.ingredientes) }
    var modoPreparo by remember { mutableStateOf(receita.modoPreparo) }
    var imagemUri by remember { mutableStateOf<Uri?>(receita.imagemUri?.let { Uri.parse(it) }) }

    val context = LocalContext.current
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imagemUri = it }
        }

    var tempoPreparo by remember { mutableStateOf(receita.tempoPreparo) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Receita") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            imagemUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            }
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("Selecionar Imagem")
            }

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
                value = tempoPreparo,
                onValueChange = { tempoPreparo = it },
                label = { Text("Tempo de Preparo (ex: 30min)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = modoPreparo,
                onValueChange = { modoPreparo = it },
                label = { Text("Modo de Preparo") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val receitaAtualizada = receita.copy(
                        titulo = titulo,
                        ingredientes = ingredientes,
                        modoPreparo = modoPreparo,
                        imagemUri = imagemUri?.toString(),
                        tempoPreparo = tempoPreparo
                    )
                    viewModel.atualizarReceita(receitaAtualizada)
                    onRecipeUpdated()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Alterações")
            }

            OutlinedButton(
                onClick = {
                    viewModel.excluirReceita(receita)
                    onRecipeDeleted()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Excluir Receita")
            }
        }
    }
}