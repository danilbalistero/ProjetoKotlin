package com.github.danilbalistero.receitasapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.github.danilbalistero.receitasapp.data.AppDatabase
import com.github.danilbalistero.receitasapp.ui.AddRecipeScreen
import com.github.danilbalistero.receitasapp.ui.EditRecipeScreen
import com.github.danilbalistero.receitasapp.ui.DetalheReceitaScreen
import com.github.danilbalistero.receitasapp.ui.theme.HomeScreen
import com.github.danilbalistero.receitasapp.ui.theme.ReceitasAppTheme
import com.github.danilbalistero.receitasapp.viewmodel.ReceitaViewModel
import com.github.danilbalistero.receitasapp.viewmodel.ReceitaViewModelFactory

class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ReceitasAppTheme {
                val dao = AppDatabase.getDatabase(applicationContext).receitaDao()
                val viewModel: ReceitaViewModel = viewModel(factory = ReceitaViewModelFactory(dao))
                val navController: NavHostController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {

                    composable("home") {
                        HomeScreen(
                            viewModel = viewModel,
                            onNovaReceitaClick = {
                                navController.navigate("add")
                            },
                            onDetalheClick = { receita ->
                                navController.navigate("detalhe/${receita.id}")
                            }
                        )
                    }

                    composable("add") {
                        AddRecipeScreen(
                            viewModel = viewModel,
                            navController = navController,
                            onRecipeSaved = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(
                        "detalhe/{receitaId}",
                        arguments = listOf(navArgument("receitaId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val receitaId = backStackEntry.arguments?.getInt("receitaId")
                        val receita = viewModel.receitas.value.find { it.id == receitaId }

                        if (receita != null) {
                            DetalheReceitaScreen(
                                receita = receita,
                                navController = navController,
                                onEditarClick = {
                                    navController.navigate("editar/${receita.id}")
                                }
                            )
                        }
                    }

                    composable(
                        "editar/{receitaId}",
                        arguments = listOf(navArgument("receitaId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val receitaId = backStackEntry.arguments?.getInt("receitaId")
                        val receita = viewModel.receitas.value.find { it.id == receitaId }

                        if (receita != null) {
                            EditRecipeScreen(
                                receita = receita,
                                viewModel = viewModel,
                                navController = navController,
                                onRecipeUpdated = {
                                    navController.popBackStack("home", inclusive = false)
                                },
                                onRecipeDeleted = {
                                    navController.popBackStack("home", inclusive = false)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}