package g54516.hirehub.model

import com.google.android.material.navigation.NavigationView

object Utils {

    private lateinit var navigation: NavigationView

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordSame(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun isPhoneNumberBelgian(number: String): Boolean {
        return number.matches("^04[0123456789]{8}\$".toRegex())
    }

    fun setNavigation(navigationView: NavigationView) {
        navigation = navigationView
    }

    fun getNavigation(): NavigationView {
        return navigation
    }

}