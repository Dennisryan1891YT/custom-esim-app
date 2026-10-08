package com.example.customesim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NetworkCell
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customesim.ui.theme.CustomESimTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomESimTheme {
                AppRoot()
            }
        }
    }
}

private data class PermissionItem(
    val title: String,
    val description: String,
    val granted: Boolean = false
)

private data class Provider(
    val name: String,
    val country: String,
    val networkType: String
)

enum class AppTheme(val label: String) {
    MATERIAL_YOU("Material You"),
    GOOGLE_MESSAGES("Google Messages"),
    LIGHT("Light Theme"),
    LIGHT_MATERIAL_YOU("Light Material You"),
    DARK("Dark Theme"),
    DARK_MATERIAL_YOU("Dark Material You Design")
}

enum class AppLanguage(val label: String) {
    ENGLISH_US("English (US)"),
    ENGLISH_CA("English (Canada)"),
    ENGLISH_IN("English (India)"),
    ENGLISH_UK("English (UK)"),
    FRENCH("French"),
    SPANISH("Spanish"),
    GERMAN("German"),
    ARABIC("Arabic"),
    HINDI("Hindi"),
    CHINESE("Chinese")
}

enum class ConnectionType(val label: String) {
    VOLTE("VoLTE"),
    G1("1G"),
    G2("2G"),
    G3("3G"),
    LTE("LTE"),
    LTE_PLUS("LTE+"),
    G4("4G"),
    G5("5G")
}

@Composable
private fun AppRoot() {
    var currentStep by remember { mutableIntStateOf(0) }
    var selectedLanguage by remember { mutableStateOf(AppLanguage.ENGLISH_US) }
    var selectedConnection by remember { mutableStateOf(ConnectionType.VOLTE) }
    var selectedProvider by remember { mutableStateOf(Provider("Global Connect", "Worldwide", "5G")) }
    var selectedTheme by remember { mutableStateOf(AppTheme.MATERIAL_YOU) }
    var setupComplete by remember { mutableStateOf(false) }

    val permissions = listOf(
        PermissionItem("Phone", "Use phone state for SIM management", true),
        PermissionItem("Call Logs", "Read call history for line diagnostics", false),
        PermissionItem("SMS", "Read and send SMS messages", false),
        PermissionItem("SMS Premium", "Allow premium messaging support", false),
        PermissionItem("Shizuku", "For system-level integration", false),
        PermissionItem("Shizuku+", "Enhanced device support", false),
        PermissionItem("Dhizuku", "Optional alternate integration", false),
        PermissionItem("Manage all files", "Access system file operations", false),
        PermissionItem("Camera", "Support for Camera1, Camera2 and CameraX APIs", false),
        PermissionItem("Microphone", "Audio capture support", false),
        PermissionItem("Wireless Debugging", "Pair with Android 11+ devices", false),
        PermissionItem("USB Debugging", "Used for Android below 11", false),
        PermissionItem("Modify system settings", "Control mobile network settings", false),
        PermissionItem("Install unknown apps", "Support app installs", false),
        PermissionItem("Location", "Find nearby SIM/E-SIM providers", false),
        PermissionItem("Disable battery optimization", "Keep service running", false)
    )

    val providers = listOf(
        Provider("Global Connect", "United States", "5G"),
        Provider("Sky Line Mobile", "Canada", "4G"),
        Provider("Prime Telecom", "India", "LTE+"),
        Provider("Euro SIM", "Germany", "5G"),
        Provider("Asia Mobile", "Japan", "LTE"),
        Provider("Atlas Data", "Brazil", "4G")
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when {
            !setupComplete && currentStep == 0 -> WelcomeScreen(
                onNext = { currentStep = 1 }
            )
            !setupComplete && currentStep == 1 -> LanguageSelectionScreen(
                selectedLanguage = selectedLanguage,
                onLanguageSelected = { selectedLanguage = it },
                onNext = { currentStep = 2 }
            )
            !setupComplete && currentStep == 2 -> PermissionsScreen(
                permissions = permissions,
                onNext = { currentStep = 3 }
            )
            !setupComplete && currentStep == 3 -> ConnectionTypeScreen(
                selectedConnection = selectedConnection,
                onConnectionSelected = { selectedConnection = it },
                onNext = { currentStep = 4 }
            )
            !setupComplete && currentStep == 4 -> ProviderSelectionScreen(
                providers = providers,
                selectedProvider = selectedProvider,
                onProviderSelected = { selectedProvider = it },
                onNext = {
                    setupComplete = true
                    currentStep = 5
                }
            )
            else -> HomeScreen(
                selectedLanguage = selectedLanguage,
                selectedConnection = selectedConnection,
                selectedProvider = selectedProvider,
                selectedTheme = selectedTheme,
                onThemeChanged = { selectedTheme = it }
            )
        }
    }
}

@Composable
private fun WelcomeScreen(onNext: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hi there!",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Android Setup",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onNext,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Next")
            }
        }
    }
}

@Composable
private fun LanguageSelectionScreen(
    selectedLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Select your language",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(AppLanguage.entries.toList()) { language ->
                Card(
                    onClick = { onLanguageSelected(language) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedLanguage == language)
                            MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = language.label)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(text = "Next")
        }
    }
}

@Composable
private fun PermissionsScreen(
    permissions: List<PermissionItem>,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Permissions",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "This app may request the following permissions for setup and service support.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(18.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(permissions) { permission ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PermDeviceInformation,
                        contentDescription = null,
                        tint = if (permission.granted) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(permission.title, fontWeight = FontWeight.SemiBold)
                        Text(
                            permission.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (permission.granted) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    }
                }
                Divider()
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(text = "Next")
        }
    }
}

@Composable
private fun ConnectionTypeScreen(
    selectedConnection: ConnectionType,
    onConnectionSelected: (ConnectionType) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "What connection type do you want?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier.selectableGroup()) {
            ConnectionType.entries.forEach { type ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (type == selectedConnection),
                            onClick = { onConnectionSelected(type) }
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (type == selectedConnection),
                        onClick = null
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(type.label, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(text = "Next")
        }
    }
}

@Composable
private fun ProviderSelectionScreen(
    providers: List<Provider>,
    selectedProvider: Provider,
    onProviderSelected: (Provider) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "What Service Provider do you want?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(providers) { provider ->
                Card(
                    onClick = { onProviderSelected(provider) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedProvider.name == provider.name)
                            MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(provider.name, fontWeight = FontWeight.Bold)
                        Text(provider.country, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Network: ${provider.networkType}")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Next")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    selectedLanguage: AppLanguage,
    selectedConnection: ConnectionType,
    selectedProvider: Provider,
    selectedTheme: AppTheme,
    onThemeChanged: (AppTheme) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Messaging", "Phone Dialer", "Settings")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("E-Sim Home")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> MessagingTab(
                    selectedLanguage = selectedLanguage,
                    selectedProvider = selectedProvider
                )
                1 -> PhoneTab(selectedConnection = selectedConnection)
                2 -> SettingsTab(
                    selectedTheme = selectedTheme,
                    onThemeChanged = onThemeChanged
                )
                else -> MessagingTab(selectedLanguage, selectedProvider)
            }
        }
    }
}

@Composable
private fun MessagingTab(
    selectedLanguage: AppLanguage,
    selectedProvider: Provider
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.PhoneAndroid,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.height(72.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "Connected Successfully!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "You can now Text & Call now.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(18.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Language: ${selectedLanguage.label}")
                Text("Service Provider: ${selectedProvider.name}")
                Text("Status: Connected")
            }
        }
    }
}

@Composable
private fun PhoneTab(selectedConnection: ConnectionType) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.SignalCellularAlt,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.height(72.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Phone Dialer",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Connection Type: ${selectedConnection.label}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { },
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Open Dialer")
        }
    }
}

@Composable
private fun SettingsTab(
    selectedTheme: AppTheme,
    onThemeChanged: (AppTheme) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(18.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Connection Types")
                Text("Change SIM/E-SIM Service Type")
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text("Themes")
                Spacer(modifier = Modifier.height(8.dp))
                AppTheme.entries.forEach { theme ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedTheme == theme,
                            onClick = { onThemeChanged(theme) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(theme.label)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("SIM / E-SIM Provider")
                Text("Global Connect")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Settings > Change SIM/E-SIM Service Type")
            }
        }
    }
}
