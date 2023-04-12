package g54516.hirehub.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import g54516.hirehub.database.dao.UserDao

/**
 * Factory class for creating instances of [LoginViewModel].
 * Allows for the injection of dependencies into the [LoginViewModel] constructor.
 *
 * @param database The [UserDao] instance used by the [LoginViewModel] to interact with the user data.
 * @param application The [Application] instance used by the [LoginViewModel] to access the application context.
 */
class LoginViewModelFactory(
    private val database: UserDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(database, application) as T
        }
        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }

}