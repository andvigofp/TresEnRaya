package com.example.tictactoe.datos

data class TicTacToeState(
    val board: List<String> = List(9) { "" },
    val currentPlayer: String = "X",
    val winner: String? = null,
    val isGameRunning: Boolean = false,
    val showRulesDialog: Boolean = false,
    val showWinnerDialog: Boolean = false,
    val isFirstGame: Boolean = true // Nuevo campo
)