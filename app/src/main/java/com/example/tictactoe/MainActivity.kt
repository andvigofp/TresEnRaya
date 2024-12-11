package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tictactoe.modelo.TicTacToeScreen
import com.example.tictactoe.ui.theme.TicTacToeTheme
import com.example.tictactoe.viewModel.TicTacToeViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: TicTacToeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Pasar el ViewModel al composable
                TicTacToeScreen(viewModel = viewModel)
            }
        }
    }
}