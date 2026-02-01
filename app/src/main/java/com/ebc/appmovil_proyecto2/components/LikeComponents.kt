package com.ebc.appmovil_proyecto2.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.rounded.DarkMode

import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


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


@Preview(showBackground = true)
@Composable
fun LikeButton(
    liked: Boolean = true,
    modifier: Modifier = Modifier
) {

    var liked by remember { mutableStateOf(liked) }

    Row(  //acomoda los componenetes de manera Horizontal
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        IconButton(
            onClick = {liked = !liked}
        ) {
            Icon(
                imageVector = if (liked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                contentDescription = "¡Me gusta!",
                tint = if (liked) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(48.dp)
            )
        }

        Text(
            text = if (liked) "¡Me gusta!" else "¿No me gusta?",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(12.dp))

    }
}