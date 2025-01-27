import androidx.compose.ui.window.ComposeUIViewController
import com.puremindit.ofa.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
