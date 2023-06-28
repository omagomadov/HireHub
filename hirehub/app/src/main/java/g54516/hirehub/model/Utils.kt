package g54516.hirehub.model

object Utils {

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordSame(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun isPhoneNumberBelgian(number: String): Boolean {
        return number.matches("^04[0123456789]{8}\$".toRegex())
    }

}