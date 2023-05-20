package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.database.dao.UserDao
import g54516.hirehub.database.dto.AppointmentDto
import g54516.hirehub.database.entity.User
import g54516.hirehub.database.repository.AppointmentRepository
import g54516.hirehub.database.service.AuthService
import kotlinx.coroutines.launch

class HomeViewModel(
    val database: AppointmentRepository,
    private var room: UserDao,
    application: Application
) : AndroidViewModel(application) {

    private var _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    private var _incomeAppointments = MutableLiveData<List<AppointmentDto>>()
    val incomeAppointments: LiveData<List<AppointmentDto>>
        get() = _incomeAppointments

    private var _passedAppointments = MutableLiveData<List<AppointmentDto>>()
    val passedAppointments: LiveData<List<AppointmentDto>>
        get() = _passedAppointments

    init {
        viewModelScope.launch {
            _user.value = room.getUserByEmail(AuthService.getCurrentUser())
            _incomeAppointments.value = database.getIncomeAppointments(_user.value?.email)
            _passedAppointments.value = database.getPassedAppointments(_user.value?.email)
        }
    }

}