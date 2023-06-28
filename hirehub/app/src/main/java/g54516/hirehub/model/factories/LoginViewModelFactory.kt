package g54516.hirehub.model.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import g54516.hirehub.database.dao.UserDao
import g54516.hirehub.model.viewmodel.LoginViewModel

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