package g54516.hirehub.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import g54516.hirehub.R
import g54516.hirehub.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        navController = findNavController(R.id.login_navigation_host)
        setSupportActionBar(binding.topBar)
        // Using 'elvis' operator to avoid NullPointerException
        // If SupportActionBar is null -> it is not a problem because code will not be executed
        supportActionBar?.let { actionBar ->
            // Display the 'back button' on the top bar
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            // 'android.R.id.home' is the 'back button' of Android
            // Don't confuse it with the ID in the resources.
            // android.R.id.home != R.id.home
            android.R.id.home -> {
                // 'onBackPressed()' is deprecated
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}