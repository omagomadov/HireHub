package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.database.dao.UserDao
import g54516.hirehub.database.entity.User
import g54516.hirehub.database.service.AuthService
import kotlinx.coroutines.launch

class HomeViewModel(
    var database: UserDao,
    application: Application
) : AndroidViewModel(application) {

    private var _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    init {
        viewModelScope.launch {
            _user.value = database.getUserByEmail(AuthService.getCurrentUser())
        }
    }

}