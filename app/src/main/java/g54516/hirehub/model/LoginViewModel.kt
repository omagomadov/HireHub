package g54516.hirehub.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import g54516.hirehub.database.HireHubDB
import g54516.hirehub.database.dao.UserDao
import java.util.Date

/**
 * ViewModel for the LoginFragment.
 *
 * @property isEmailValid A LiveData object indicating that an email address is valid or not.
 * @property displayToast A LiveData object indicating that an toast must be displayed or not.
 */
class LoginViewModel(val database : UserDao,
                     application: Application) : AndroidViewModel(application) {

    private var _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean>
        get() = _isEmailValid

    private var _displayToast = MutableLiveData<Boolean>()
    val displayToast: LiveData<Boolean>
        get() = _displayToast

    //private val context = application

    init {
        _isEmailValid.value = false
        _displayToast.value = false
    }

    /**
     * Saves the given email and date in the database.
     *
     * If the email already exists in the database, the date will be updated.
     * If the email does not exist in the database, a new record with the email and date will be created.
     *
     * This method sets the value of [_isEmailValid] to indicate if the email address is valid or not.
     *
     * @param date The date to store in the database.
     * @param email The email address to store in the database.
     */
    fun save(date: Date, email: String) {
        _isEmailValid.value = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Setter for [displayToast] attribute.
     *
     * This attribute controls whether or not toast messages should be displayed.
     *
     * @param isOn to set the [displayToast]
     */
    fun setDisplayToast(isOn: Boolean) {
        _displayToast.value = isOn
    }

}