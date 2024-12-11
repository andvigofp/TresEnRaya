package com.example.tictactoe.modelo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tictactoe.viewModel.TicTacToeViewModel


@Composable
fun TicTacToeScreen(viewModel: TicTacToeViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Mostrar el tablero
        TicTacToeBoard(
            board = state.board,
            onCellClick = { index -> viewModel.onCellClick(index) },
            enabled = state.isGameRunning
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar texto de turno o ganador
        if (state.winner != null) {
            Text(
                text = if (state.winner == "Draw") "¡Empate!" else "¡Ganador: ${state.winner}!",
                style = MaterialTheme.typography.titleMedium
            )
        } else if (state.isGameRunning) {
            Text(
                text = "Turno: ${state.currentPlayer}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar botón "Play Game" solo si el juego no está en curso
        if (!state.isGameRunning) {
            Button(onClick = { viewModel.startGame() }) {
                Text(text = "Jugar")
            }
        }
    }

    // Diálogo de reglas
    if (state.showRulesDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissRulesDialog() },
            title = { Text(text = "Reglas del Tres en Raya") },
            text = {
                Text(
                    text = """
                    - El juego es para dos jugadores.
                    - Cada jugador toma turnos para colocar su símbolo (X o O) en una celda.
                    - Gana el jugador que conecte tres símbolos en línea recta, diagonal o vertical.
                    - Si todas las celdas están ocupadas y nadie ha ganado, es un empate.
                    ¡Diviértete!
                    """.trimIndent()
                )
            },
            confirmButton = {
                Button(onClick = { viewModel.dismissRulesDialog() }) {
                    Text(text = "OK, empieza el juego")
                }
            }
        )
    }

    // Diálogo de felicitación
    if (state.showWinnerDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "¡Enhorabuena!") },
            text = {
                Text(
                    text = if (state.winner == "Draw") "¡Es un empate!" else "¡Enhorabuena, ${state.winner} ganó!"
                )
            },
            confirmButton = {
                Button(onClick = { viewModel.dismissWinnerDialog() }) {
                    Text(text = "OK")
                }
            }
        )
    }

    // Diálogo para continuar o salir
    if (state.winner != null && !state.showWinnerDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Juego terminado") },
            text = { Text(text = "¿Quieres seguir jugando?") },
            confirmButton = {
                Button(onClick = { viewModel.startGame() }) {
                    Text(text = "Seguir jugando")
                }
            },
            dismissButton = {
                Button(onClick = { viewModel.exitGame() }) {
                    Text(text = "Salir")
                }
            }
        )
    }
}