package dev.eagelware.phonex

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.Navigation
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.eagelware.phonex.view.AccountScreen
import dev.eagelware.phonex.view.ContactListScreen
import dev.eagelware.phonex.view.GroupListScreen
import kotlinx.serialization.Serializable

@Serializable
sealed interface QRoute: NavKey

@Serializable
data object HomeEntry: QRoute

@Serializable
data object GroupEntry: QRoute

@Serializable
data object AccountEntry: QRoute

enum class NavItem(
    val route: NavKey,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
){
    HOME(
        route = HomeEntry,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        label = "Home"
    ),
    GROUP(
        route = GroupEntry,
        selectedIcon = Icons.Filled.Group,
        unselectedIcon = Icons.Outlined.Group,
        label = "Groups"
    ),
    ACCOUNT(
    route = AccountEntry,
    selectedIcon = Icons.Filled.AccountCircle,
    unselectedIcon = Icons.Outlined.AccountCircle,
    label = "Account"
    )
}

@Composable
fun Navigations(
    backStack: NavBackStack<NavKey>,
    modifier: Modifier,
){
    NavDisplay(
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryProvider = entryProvider {
            entry<HomeEntry>{
                ContactListScreen()
            }
            entry<GroupEntry>{
                GroupListScreen()
            }
            entry<AccountEntry>{
                AccountScreen()
            }
        },
//        transitionSpec = {
//            slideInHorizontally(initialOffsetX = {it}) togetherWith
//        }
    )
}

fun customNavigationSuiteType(
    windowSizeClass: WindowSizeClass,
): NavigationSuiteType{
    val width = windowSizeClass.widthSizeClass
    val height = windowSizeClass.heightSizeClass

    return when(width){
        WindowWidthSizeClass.Compact -> {
            NavigationSuiteType.ShortNavigationBarCompact
        }

        WindowWidthSizeClass.Medium -> {
            when(height) {
                WindowHeightSizeClass.Compact-> NavigationSuiteType.NavigationBar
                WindowHeightSizeClass.Medium,
                WindowHeightSizeClass.Expanded->
                    NavigationSuiteType.ShortNavigationBarMedium
                else ->
                    NavigationSuiteType.ShortNavigationBarMedium
            }
        }

        WindowWidthSizeClass.Expanded->{
            when(height) {
                WindowHeightSizeClass.Expanded -> {
                    NavigationSuiteType.NavigationDrawer
                }

                WindowHeightSizeClass.Compact -> {
                    NavigationSuiteType.NavigationRail
                }

                WindowHeightSizeClass.Medium-> {
                    NavigationSuiteType.WideNavigationRailCollapsed
                }
                else -> NavigationSuiteType.WideNavigationRailCollapsed
            }
        }
        else-> NavigationSuiteType.ShortNavigationBarCompact
    }
}