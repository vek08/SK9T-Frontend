package com.k9t.scoreknight.auth.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.k9t.scoreknight.auth.domain.RegisterRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    onSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    LaunchedEffect(isLoggedIn, uiState) {
        if (isLoggedIn || uiState is AuthUiState.Success) {
            viewModel.resetState()
            onSuccess()
        }
    }

    Scaffold(
        containerColor = Color(0xFF1E2822),
        topBar = {
            TopAppBar(
                title = { Text("Sign Up", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedContainerColor = Color(0xFF2A362E),
                    focusedContainerColor = Color(0xFF2A362E),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFFC0F050)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedContainerColor = Color(0xFF2A362E),
                    focusedContainerColor = Color(0xFF2A362E),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFFC0F050)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedContainerColor = Color(0xFF2A362E),
                    focusedContainerColor = Color(0xFF2A362E),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFFC0F050)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedContainerColor = Color(0xFF2A362E),
                    focusedContainerColor = Color(0xFF2A362E),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFFC0F050)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { 
                    if (password != confirmPassword) {
                        errorMsg = "Passwords do not match"
                    } else if (email.isBlank() || name.isBlank() || password.isBlank()) {
                        errorMsg = "Please fill all fields"
                    } else {
                        errorMsg = ""
                        viewModel.register(RegisterRequest(name, email, password)) 
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC0F050))
            ) {
                if (uiState is AuthUiState.Loading) {
                    CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
                } else {
                    Text("Create Account", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
            
            if (errorMsg.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(errorMsg, color = Color.Red)
            } else if (uiState is AuthUiState.Error) {
                Spacer(modifier = Modifier.height(16.dp))
                Text((uiState as AuthUiState.Error).message, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Row {
                Text("Already have an account? ", color = Color.Gray)
                Text(
                    "Login", 
                    color = Color(0xFFC0F050), 
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }
    }
}
