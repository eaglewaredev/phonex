package dev.eagelware.phonex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import dev.eagelware.phonex.theme.AColorPalette
import dev.eagelware.phonex.theme.ETheme
import dev.eagelware.phonex.theme.LocalTheme
import dev.eagelware.phonex.theme.isDark

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(LocalTheme provides LocalTheme.current) {
                ETheme(darkTheme = LocalTheme.current.value) {
                    val barColor = AColorPalette.current.background.toArgb()
                    val fontColor = AColorPalette.current.onBackground.toArgb()

                    LaunchedEffect(isDark.value) {
                        if (!isDark.value) {
                            enableEdgeToEdge(
                                statusBarStyle = SystemBarStyle.light(
                                    fontColor,barColor,
                                ),
                                navigationBarStyle = SystemBarStyle.light(
                                    fontColor,barColor,
                                ),
                            )
                        } else {
                            enableEdgeToEdge(
                                statusBarStyle = SystemBarStyle.dark(
                                    barColor,
                                ),
                                navigationBarStyle = SystemBarStyle.dark(
                                    barColor,
                                ),
                            )
                        }
                    }

                    App()
                }

            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}