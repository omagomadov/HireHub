package g54516.hirehub.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import g54516.hirehub.R
import g54516.hirehub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.main_navigation_host)
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

                R.id.message -> {
                    navController.navigate(R.id.messageFragment)
                    true
                }

                R.id.setting -> {
                    navController.navigate(R.id.settingFragment)
                    Log.i("setting", "clicked")
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
                R.id.home, R.id.search, R.id.message, R.id.setting -> true
                else -> false
            }
        }
    }
}