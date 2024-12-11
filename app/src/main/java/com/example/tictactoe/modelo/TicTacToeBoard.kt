package com.example.tictactoe.modelo

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TicTacToeBoard(
    board: List<String>,
    onCellClick: (Int) -> Unit,
    enabled: Boolean
) {
    Column {
        for (row in 0..2) {
            Row {
                for (col in 0..2) {
                    val index = row * 3 + col
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .border(1.dp, MaterialTheme.colorScheme.onBackground)
                            .clickable(enabled = enabled && board[index].isEmpty()) {
                                onCellClick(index)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = board[index],
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }
        }
    }
}