package g54516.hirehub.ui.activities

import android.os.Bundle
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

    private fun setupBottomBarSelectListener() {
        // Listeners when clicked on the corresponding menu in bottom bar
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.search -> {
                    navController.navigate(R.id.action_homeFragment_to_searchFragment)
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
            when(item.itemId) {
                R.id.home -> {
                    true
                }
                R.id.search -> {
                    true
                }
                else -> false
            }
        }
    }
}