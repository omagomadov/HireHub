package g54516.hirehub.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import g54516.hirehub.R
import g54516.hirehub.database.service.AuthService
import g54516.hirehub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.main_navigation_host)
        binding.topBar.title = ""
        setupVisibilityOfMenu()
        setupBottomBarSelectListener()
        setupBottomBarReselectListeners()
        setSupportActionBar(binding.topBar)
    }

    override fun onBackPressed() {
        navController.navigate(R.id.homeFragment)
        binding.bottomNavigation.selectedItemId = R.id.home
    }

    private fun setupBottomBarSelectListener() {
        // Listeners when clicked on the corresponding menu in bottom bar
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.search -> {
                    navController.navigate(R.id.searchFragment)
                    true
                }

                R.id.pending -> {
                    navController.navigate(R.id.pendingFragment)
                    true
                }

                R.id.setting -> {
                    navController.navigate(R.id.settingFragment)
                    true
                }

                R.id.logout -> {
                    Toast.makeText(this, R.string.logout_message, Toast.LENGTH_SHORT).show()
                    AuthService.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    this.finish()
                    true
                }

                else -> false
            }
        }
    }

    private fun setupBottomBarReselectListeners() {
        // Listeners when 're-clicked' on the corresponding menu in bottom bar
        // (if not set -> app crash !!)
        binding.bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.home, R.id.search, R.id.logout, R.id.setting -> true
                else -> false
            }
        }
    }

    private fun setupVisibilityOfMenu() {
        if (intent.getBooleanExtra("isDeveloper", false)) {
            binding.bottomNavigation.menu.findItem(R.id.search).isVisible = false
            binding.bottomNavigation.menu.findItem(R.id.pending).isVisible = true
        } else {
            binding.bottomNavigation.menu.findItem(R.id.search).isVisible = true
            binding.bottomNavigation.menu.findItem(R.id.pending).isVisible = false
        }
    }
}