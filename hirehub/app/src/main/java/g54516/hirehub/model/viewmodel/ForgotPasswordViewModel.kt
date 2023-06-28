package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import g54516.hirehub.R
import g54516.hirehub.database.service.AuthService
import g54516.hirehub.model.Utils.isEmailValid

class ForgotPasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val application = application

    private var _displayToast = MutableLiveData<Boolean>()
    val displayToast: LiveData<Boolean>
        get() = _displayToast

    private var _notification = MutableLiveData<String>()
    val notification: LiveData<String>
        get() = _notification

    private var _emailSent = MutableLiveData<Boolean>()
    val emailSent: LiveData<Boolean>
        get() = _emailSent

    init {
        _displayToast.value = false
        _emailSent.value = false
    }

    fun sendResetPasswordEmail(email: String) {
        _displayToast.value = true
        if (isEmailValid(email)) {
            AuthService.sendResetPasswordEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _notification.value = application.getString(R.string.forgot_password_successful)
                    _emailSent.value = true
                } else {
                    _notification.value =
                        application.getString(R.string.forgot_password_unsuccessful)
                }
            }
        } else {
            _notification.value = application.getString(R.string.signin_invalide_email)
        }
    }

    fun setDisplayToast(isDisplayed: Boolean) {
        _displayToast.value = isDisplayed
    }

}