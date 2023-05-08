package g54516.hirehub.model

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import g54516.hirehub.R

object Utils {

    private lateinit var navigation: NavigationView
    private lateinit var bottomBarNavigation: BottomNavigationView

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordSame(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun isPhoneNumberBelgian(number: String): Boolean {
        return number.matches("^04[0123456789]{8}\$".toRegex())
    }

    fun hideBottomBarNavigation() {
        if(bottomBarNavigation.visibility == View.VISIBLE) {
            bottomBarNavigation.visibility = View.GONE
        }
    }

    fun displayBottomBarNavigation() {
        if(bottomBarNavigation.visibility == View.GONE) {
            bottomBarNavigation.visibility = View.VISIBLE
        }
    }

    fun hideLoggedMenu() {
        navigation.menu.setGroupVisible(R.id.logged_menu, false)
    }

    fun displayLoggedMenu() {
        navigation.menu.setGroupVisible(R.id.logged_menu, true)
    }

    fun setNavigation(navigationView: NavigationView) {
        navigation = navigationView
    }

    fun getNavigation(): NavigationView {
        return navigation
    }

    fun setBottomBarNavigation(view: BottomNavigationView) {
        bottomBarNavigation = view
    }

}