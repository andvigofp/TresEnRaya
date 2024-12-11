package com.example.tictactoe.viewModel

import androidx.lifecycle.ViewModel
import com.example.tictactoe.datos.TicTacToeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.system.exitProcess


// ViewModel que maneja la lógica
class TicTacToeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TicTacToeState())
    val uiState: StateFlow<TicTacToeState> = _uiState

    fun startGame() {
        _uiState.update { currentState ->
            TicTacToeState(
                board = List(9) { "" },
                currentPlayer = "X",
                winner = null,
                isGameRunning = true,
                showRulesDialog = currentState.isFirstGame, // Mostrar reglas solo la primera vez
                isFirstGame = false // Actualizar para no mostrar más las reglas
            )
        }
    }

    fun dismissRulesDialog() {
        _uiState.update { it.copy(showRulesDialog = false) }
    }

    fun onCellClick(index: Int) {
        val state = _uiState.value
        if (!state.isGameRunning || state.board[index].isNotEmpty() || state.winner != null) {
            return
        }

        // Colocar el movimiento del jugador
        val updatedBoard = state.board.toMutableList()
        updatedBoard[index] = state.currentPlayer

        // Comprobar si hay un ganador
        val winner = checkWinner(updatedBoard)

        _uiState.update {
            it.copy(
                board = updatedBoard,
                winner = winner,
                currentPlayer = if (winner == null) "O" else state.currentPlayer,
                isGameRunning = winner == null,
                showWinnerDialog = winner != null // Mostrar diálogo si hay ganador
            )
        }

        // Si no hay ganador, turno de la máquina
        if (winner == null) {
            playMachineTurn()
        }
    }

    private fun playMachineTurn() {
        val state = _uiState.value
        val emptyCells = state.board.mapIndexedNotNull { index, cell ->
            if (cell.isEmpty()) index else null
        }

        // Si no hay celdas vacías, no hacer nada
        if (emptyCells.isEmpty()) return

        // Elegir una celda aleatoria
        val randomIndex = emptyCells.random()
        val updatedBoard = state.board.toMutableList()
        updatedBoard[randomIndex] = "O"

        // Comprobar si hay un ganador después del turno de la máquina
        val winner = checkWinner(updatedBoard)

        _uiState.update {
            it.copy(
                board = updatedBoard,
                winner = winner,
                currentPlayer = if (winner == null) "X" else state.currentPlayer,
                isGameRunning = winner == null,
                showWinnerDialog = winner != null // Mostrar diálogo si hay ganador
            )
        }
    }

    private fun checkWinner(board: List<String>): String? {
        val winningPositions = listOf(
            listOf(0, 1, 2), // Filas
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6), // Columnas
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8), // Diagonales
            listOf(2, 4, 6)
        )

        for (positions in winningPositions) {
            val (a, b, c) = positions
            if (board[a].isNotEmpty() && board[a] == board[b] && board[b] == board[c]) {
                return board[a] // Ganador
            }
        }

        return if (board.all { it.isNotEmpty() }) "Draw" else null // Empate o sin ganador
    }

    fun dismissWinnerDialog() {
        _uiState.update { it.copy(showWinnerDialog = false) }
    }

    fun exitGame() {
        exitProcess(0)
    }
}