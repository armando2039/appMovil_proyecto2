package com.ebc.appmovil_proyecto2.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode

import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Preview(showBackground = true)
@Composable
fun ThemeToggleIcon(
    isDark: Boolean = true,
    onToggle: () -> Unit = {},
    modifier: Modifier = Modifier

) {
    IconButton(
        onClick = onToggle,
    ) {
        Icon(
            if (isDark)
                Icons.Rounded.DarkMode //entrar modo socuro
            else
                Icons.Rounded.LightMode, //salir modo oscuro
            contentDescription = "Cambiar tema"
        )
    }
}

