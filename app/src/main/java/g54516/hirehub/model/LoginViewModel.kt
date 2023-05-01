package g54516.hirehub.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.database.dao.UserDao
import g54516.hirehub.database.entity.User
import kotlinx.coroutines.launch
import java.util.Date

class LoginViewModel(
    //FIXME (QHB) :could be private.
    val database: UserDao,
    application: Application
) : AndroidViewModel(application) {

    private var _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean>
        get() = _isEmailValid

    private var _displayToast = MutableLiveData<Boolean>()
    val displayToast: LiveData<Boolean>
        get() = _displayToast

    var emails : LiveData<List<String>> = database.getAllEmails()

    init {
        _isEmailValid.value = false
        _displayToast.value = false
    }

    fun checkEmail(email: String, date: Date) {
        _isEmailValid.value = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (isEmailValid.value == true) {
            //FIXME (QHB) : you can use coroutines only if you can explain them during the examination.
            // Otherwise you can use the option allowMainThreadQueries when you build the database.
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
                //FIXME (QHB) :use Log.d instead of println (or better, just use the debugger
                // instead of debug printing)
                println(database.getAllUsers().toString())
            }
        }
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

    fun setDisplayToast(isOn: Boolean) {
        _displayToast.value = isOn
    }

}