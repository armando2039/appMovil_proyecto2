package com.ebc.appmovil_proyecto2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ebc.appmovil_proyecto2.components.ThemeToggleIcon
import com.ebc.appmovil_proyecto2.ui.theme.AppMovil_proyecto2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var dark by rememberSaveable { mutableStateOf(false) }

            AppMovil_proyecto2Theme(darkTheme = dark, dynamicColor = true) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ThemeToggleIcon(
                        isDark = dark,
                        onToggle = {dark = !dark },
                        modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppMovil_proyecto2Theme {
        Greeting("Android")
    }
}