package g54516.hirehub.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.database.dao.UserDao
import g54516.hirehub.database.entity.User
import kotlinx.coroutines.launch
import java.util.*

/**
 * ViewModel for the LoginFragment.
 *
 * @property isEmailValid A LiveData object indicating that an email address is valid or not.
 * @property displayToast A LiveData object indicating that an toast must be displayed or not.
 */
class LoginViewModel(
    val database: UserDao,
    application: Application
) : AndroidViewModel(application) {

    private var _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean>
        get() = _isEmailValid

    private var _displayToast = MutableLiveData<Boolean>()
    val displayToast: LiveData<Boolean>
        get() = _displayToast

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
    fun checkEmail(email: String, date: Date) {
        _isEmailValid.value = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (isEmailValid.value == true) {
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
                println(database.getAllUsers().toString())
            }
        }
    }

    /**
     * Insert a new user with the given email and date information.
     *
     * @param email the email of the user
     * @param date the date of the user
     */
    private fun insert(email: String, date: Date) {
        viewModelScope.launch {
            val newUser = User(email = email, date = date)
            database.insert(newUser)
        }
    }

    /**
     * Update an existing user with the latest date.
     *
     * @param userId the ID of the user.
     * @param email the email of the user.
     * @param date the date of the user.
     *
     * @throws IllegalArgumentException if the user ID is invalid or the email is blank.
     */
    private fun update(userId: Int, email: String, date: Date) {
        viewModelScope.launch {
            val newUser = User(userId = userId, email = email, date = date)
            database.update(newUser)
        }
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