package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.R
import g54516.hirehub.database.dao.UserDao
import g54516.hirehub.database.dto.UserDto
import g54516.hirehub.database.entity.User
import g54516.hirehub.database.repository.DeveloperRepository
import g54516.hirehub.database.repository.UserRepository
import g54516.hirehub.database.service.AuthService
import g54516.hirehub.model.Utils.isEmailValid
import kotlinx.coroutines.launch
import java.util.Date

class LoginViewModel(
    private val database: UserDao,
    application: Application
) : AndroidViewModel(application) {

    private val application = application

    private val _userRepository = UserRepository()

    private val _developerRepository = DeveloperRepository()

    private var _displayToast = MutableLiveData<Boolean>()
    val displayToast: LiveData<Boolean>
        get() = _displayToast

    private var _notification = MutableLiveData<String>()
    val notification: LiveData<String>
        get() = _notification

    private var _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    var emails: LiveData<List<String>> = database.getAllEmails()

    init {
        _displayToast.value = false
        _isConnected.value = false
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

    private fun insert(user: UserDto, date: Date) {
        viewModelScope.launch {
            val newUser = User(
                email = user.email, firstName = user.firstName,
                lastName = user.lastName, gender = user.gender,
                phoneNumber = user.phoneNumber, date = date
            )
            database.insert(newUser)
        }
    }

    private fun update(userId: Int, user: UserDto, date: Date) {
        viewModelScope.launch {
            val newUser = User(
                userId = userId, email = user.email, firstName = user.firstName,
                lastName = user.lastName, gender = user.gender,
                phoneNumber = user.phoneNumber, date = date
            )
            database.update(newUser)
        }
    }

    private fun updateLocalDatabase(email: String, date: Date) {
        viewModelScope.launch {
            val user = _userRepository.getUserByEmail(email)
            if (user != null) {
                if (database.getUserByEmail(email) == null) {
                    insert(user, date)
                } else {
                    val oldUser = database.getUserByEmail(email)
                    if (oldUser?.date != null) {
                        if (oldUser.date.before(date)) {
                            update(oldUser.userId, user, date)
                        }
                    }
                }
                _isConnected.value = true
            } else {
                val developer = _developerRepository.getDeveloperByEmail(email)
                if (developer != null) {
                    val user = UserDto(
                        developer.email,
                        developer.firstName, developer.lastName,
                        developer.gender, developer.phoneNumber
                    )
                    if (database.getUserByEmail(email) == null) {
                        insert(user, date)
                    } else {
                        val oldUser = database.getUserByEmail(email)
                        if (oldUser?.date != null) {
                            if (oldUser.date.before(date)) {
                                update(oldUser.userId, user, date)
                            }
                        }
                    }
                }
                _isConnected.value = true
            }
        }
    }

    private fun isFormCompleted(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            _notification.value = application.getString(R.string.form_not_completed)
            return false
        } else if (!isEmailValid(email)) {
            _notification.value = application.getString(R.string.signin_invalide_email)
            return false
        }
        return true
    }

}