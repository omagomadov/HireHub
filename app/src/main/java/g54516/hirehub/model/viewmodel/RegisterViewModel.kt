package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.R
import g54516.hirehub.database.dto.UserDto
import g54516.hirehub.database.repository.UserRepository
import g54516.hirehub.database.service.AuthService
import g54516.hirehub.model.Utils.isEmailValid
import g54516.hirehub.model.Utils.isPasswordSame
import g54516.hirehub.model.Utils.isPhoneNumberBelgian
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val application = application

    private var _repository: UserRepository

    private var _displayToast = MutableLiveData<Boolean>()
    val displayToast: LiveData<Boolean>
        get() = _displayToast

    private var _notification = MutableLiveData<String>()
    val notification: LiveData<String>
        get() = _notification

    private var _isRegistered = MutableLiveData<Boolean>()
    val isRegistered: LiveData<Boolean>
        get() = _isRegistered

    init {
        _displayToast.value = false
        _isRegistered.value = false
        _repository = UserRepository()
    }

    fun register(
        email: String,
        firstname: String,
        lastname: String,
        password: String,
        confirmPassword: String,
        phone: String,
        gender: String
    ) {
        _displayToast.value = true
        if (isFormCompleted(email, firstname, lastname, password, confirmPassword, phone, gender)) {
            AuthService.signUp(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _notification.value =
                        application.getString(R.string.signup_successful_notification)
                    _isRegistered.value = true
                    AuthService.signIn(email, password)
                    insert(UserDto(email, firstname, lastname, gender, phone.toInt()))
                } else {
                    _notification.value =
                        application.getString(R.string.signup_unsuccessful_notification)
                }
            }
        }
    }

    fun setDisplayToast(isDisplayed: Boolean) {
        _displayToast.value = isDisplayed
    }

    private fun insert(user: UserDto) {
        viewModelScope.launch {
            _repository.add(user)
        }
    }

    private fun isFormCompleted(
        email: String,
        firstname: String,
        lastname: String,
        password: String,
        confirmPassword: String,
        phone: String,
        gender: String
    ): Boolean {
        val errorMessage = when {
            email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()
                    || firstname.isEmpty() || lastname.isEmpty() ->
                R.string.form_not_completed

            !isEmailValid(email) -> R.string.signin_invalide_email
            !isPasswordSame(password, confirmPassword) -> R.string.signup_password_not_same
            !isPhoneNumberBelgian(phone) -> R.string.signup_phone_number
            gender.isEmpty() -> R.string.signup_gender_unsuccessful
            else -> null
        }
        errorMessage?.let {
            _notification.value = application.getString(it)
            return false
        }
        return true
    }

}