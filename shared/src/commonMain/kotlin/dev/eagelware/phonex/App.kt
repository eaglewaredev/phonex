package dev.eagelware.phonex

import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.eagelware.phonex.components.NSuiteScaffold
import dev.eagelware.phonex.theme.ETheme
import dev.eagelware.phonex.theme.LocalTheme
import dev.eagelware.phonex.theme.isDark
import dev.eagelware.phonex.viewmodel.UserSettingsViewModel
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.java.KoinJavaComponent.inject


@Composable
@Preview
fun App(userSettingsViewModel: UserSettingsViewModel = koinViewModel()) {
    val userSettings by userSettingsViewModel.userSettings.collectAsState()
    isDark.value = userSettings.darkTheme
    CompositionLocalProvider(LocalTheme provides LocalTheme.current){
        ETheme(LocalTheme.current.value) {
            val config = SavedStateConfiguration {
                serializersModule = SerializersModule {
                    polymorphic(NavKey::class){
                        mutableStateListOf(QRoute)
                        subclass(HomeEntry::class, HomeEntry.serializer())
                    }
                }
            }

            val backStack = rememberNavBackStack(config, HomeEntry)
            val windowSizeClass = calculateWindowSizeClass()
            val customLayoutType = customNavigationSuiteType(windowSizeClass)
            val navigationSuiteState = rememberNavigationSuiteScaffoldState()

            LaunchedEffect(backStack.lastOrNull()){
                if(backStack.lastOrNull() in mainAppScreens){
                    navigationSuiteState.show()
                }else{
                    navigationSuiteState.hide()
                }
            }

            NSuiteScaffold(
                navigationSuiteState = navigationSuiteState,
                layoutType = customLayoutType,
                backStack = backStack,
                content = {
                    Navigations(
                        backStack = backStack,
                        modifier = Modifier,
                    )
                }
            )
        }
    }
}

val mainAppScreens = listOf(
    HomeEntry,
    GroupEntry,
    AccountEntry,
)