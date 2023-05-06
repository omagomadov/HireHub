package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import g54516.hirehub.R
import g54516.hirehub.database.service.AuthService
import g54516.hirehub.model.Utils.isEmailValid
import g54516.hirehub.model.Utils.isPasswordSame
import g54516.hirehub.model.Utils.isPhoneNumberBelgian

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val application = application

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
    }

    fun register(
        email: String, password: String, confirmPassword: String,
        phone: String, gender: String
    ) {
        _displayToast.value = true
        if (isFormCompleted(email, password, confirmPassword, phone, gender)) {
            AuthService.signUp(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _notification.value =
                        application.getString(R.string.signup_successful_notification)
                    _isRegistered.value = true
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

    private fun isFormCompleted(
        email: String, password: String, confirmPassword: String,
        phone: String, gender: String
    ): Boolean {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
            _notification.value = application.getString(R.string.form_not_completed)
            return false
        } else if (!isEmailValid(email)) {
            _notification.value = application.getString(R.string.signin_invalide_email)
            return false
        } else if (!isPasswordSame(password, confirmPassword)) {
            _notification.value = application.getString(R.string.signup_password_not_same)
            return false
        } else if (!isPhoneNumberBelgian(phone)) {
            _notification.value = application.getString(R.string.signup_phone_number)
            return false
        } else if (gender.isEmpty()) {
            _notification.value = application.getString(R.string.signup_gender_unsuccessful)
            return false
        }
        return true
    }

}