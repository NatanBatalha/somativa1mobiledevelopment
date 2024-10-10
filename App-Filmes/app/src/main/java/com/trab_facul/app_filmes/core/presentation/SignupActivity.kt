package com.trab_facul.app_filmes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.trab_facul.app_filmes.core.presentation.MainActivity

class SignupActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignupScreen(
                onSignupClick = { email, password -> registerUser(email, password) },
                onMoveToLogin = { navigateToLogin() }
            )
        }

        // Initializing Firebase Auth
        auth = Firebase.auth
    }

    // Function to register the user
    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email.trim(), password.trim())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    // Navigate to MainActivity on successful signup
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Function to navigate to the LoginActivity
    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    // Check if the user is already logged in
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    companion object {
        private const val TAG = "SignupActivity"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(onSignupClick: (String, String) -> Unit, onMoveToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF181818)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                // backgroundColor = Color.Transparent
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                // backgroundColor = Color.Transparent
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { onSignupClick(email, password) }) {
            Text(text = "Continue", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onMoveToLogin) {
            Text(
                text = "Already have an account? Login here",
                color = Color(0xFF03A9F4)
            )
        }
    }
}
