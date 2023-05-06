package g54516.hirehub.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import g54516.hirehub.R
import g54516.hirehub.databinding.ActivityMainBinding
import g54516.hirehub.model.Utils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationController: NavController
    private lateinit var drawer: DrawerLayout
    private lateinit var appBar: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navigationController = this.findNavController(R.id.navigation_host)
        drawer = binding.drawerLayout
        // Define view who are considered as top level
        appBar = AppBarConfiguration(
            setOf(
                R.id.loginFragment,
                R.id.homeFragment
            ), drawer
        )
        // Displaying navigation drawer (need to swipe left to right)
        NavigationUI.setupWithNavController(binding.navigationView, navigationController)
        // Displaying the 'sandwich' icon
        NavigationUI.setupActionBarWithNavController(this, navigationController, appBar)
        Utils.setNavigation(binding.navigationView)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navigationController, appBar)
    }
}