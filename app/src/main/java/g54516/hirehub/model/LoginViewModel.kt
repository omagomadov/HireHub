package g54516.hirehub.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.R
import g54516.hirehub.database.dao.UserDao
import g54516.hirehub.database.entity.User
import g54516.hirehub.database.service.AuthService
import kotlinx.coroutines.launch
import java.util.Date

class LoginViewModel(
    private val database: UserDao,
    application: Application
) : AndroidViewModel(application) {

    private val application = application

    private var _displayToast = MutableLiveData<Boolean>()
    val displayToast: LiveData<Boolean>
        get() = _displayToast

    private var _notification = MutableLiveData<String>()
    val notification: LiveData<String>
        get() = _notification

    var emails: LiveData<List<String>> = database.getAllEmails()

    init {
        _displayToast.value = false
    }

    fun signIn(email: String, password: String, date: Date) {
        _displayToast.value = true
        if (isFormCompleted(email, password)) {
            AuthService.signIn(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _notification.value = application
                        .getString(R.string.signin_successful_notification)
                    updateLocalDatabase(email, date)
                } else {
                    _notification.value = application
                        .getString(R.string.signin_unsuccessful_notification)
                }
            }
        }
    }

    fun setDisplayToast(isDisplayed: Boolean) {
        _displayToast.value = isDisplayed
    }

    private fun insert(email: String, date: Date) {
        viewModelScope.launch {
            val newUser = User(email = email, date = date)
            database.insert(newUser)
        }
    }

    private fun update(userId: Int, email: String, date: Date) {
        viewModelScope.launch {
            val newUser = User(userId = userId, email = email, date = date)
            database.update(newUser)
        }
    }

    private fun updateLocalDatabase(email: String, date: Date) {
        viewModelScope.launch {
            if (database.getUserByEmail(email) == null) {
                insert(email, date)
            } else {
                val oldUser = database.getUserByEmail(email)
                if (oldUser?.date != null) {
                    if (oldUser.date.before(date)) {
                        update(oldUser.userId, email, date)
                    }
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isFormCompleted(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            _notification.value = application.getString(R.string.form_not_completed)
            return false
        } else if (!isEmailValid(email)) {
            _notification.value = application.getString(R.string.signin_invalide_email)
        }
        return true
    }

}