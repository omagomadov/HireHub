package g54516.hirehub.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import g54516.hirehub.R
import g54516.hirehub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationController: NavController
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navigationController = this.findNavController(R.id.navigation_host)
        drawer = binding.drawerLayout
        // Displaying navigation drawer (need to swipe left to right)
        NavigationUI.setupWithNavController(binding.navigationView, navigationController)
        // Displaying the 'sandwich' icon
        NavigationUI.setupActionBarWithNavController(this, navigationController, drawer)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navigationController, drawer)
    }
}