package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.database.dto.UserDto
import g54516.hirehub.database.repository.AppointmentRepository
import g54516.hirehub.database.repository.UserRepository
import g54516.hirehub.database.service.AuthService
import kotlinx.coroutines.launch
import java.time.LocalDate

class AppointmentViewModel(
    private val database: AppointmentRepository,
    val developer: DeveloperDto?,
    application: Application
) : AndroidViewModel(application) {

    private val _userRepository = UserRepository()

    private var _currentUser = MutableLiveData<UserDto>()
    val currentUser: LiveData<UserDto>
        get() = _currentUser

    private var _appointmentDate = MutableLiveData<LocalDate>()
    val appointmentDate: LiveData<LocalDate>
        get() = _appointmentDate

    private var _appointmentHour = MutableLiveData<Int>()
    val appointmentHour: LiveData<Int>
        get() = _appointmentHour

    private var _userSelectedValidDate = MutableLiveData<Boolean>()
    val userSelectedValidDate: LiveData<Boolean>
        get() = _userSelectedValidDate

    private var _timeSlots = MutableLiveData<List<Int>>()
    val timeSlots: LiveData<List<Int>>
        get() = _timeSlots

    init {
        _appointmentDate.value = LocalDate.now()
        _userSelectedValidDate.value = true
        _timeSlots.value = listOf(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
        _appointmentHour.value = 10
        getCurrentUser()
    }

    fun addAppointment(user_email: String, developer: DeveloperDto, date: LocalDate?) {
        val hours = appointmentHour.value
        if (userSelectedValidDate.value == true) {
            if (date != null && hours != null) {
                // Add on the LocalDate => chosen hours
                val dateWithHours = date.atStartOfDay().plusHours(hours.toLong())
                _currentUser.value?.let {
                    database.addAppointment(it, developer, dateWithHours)
                }
            }
        }
    }

    fun updateDate(date: LocalDate) {
        _appointmentDate.value = date
        _userSelectedValidDate.value = date.isAfter(LocalDate.now())
    }

    fun updateHour(hour: Int) {
        _appointmentHour.value = hour
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            _currentUser.value = _userRepository.getUserByEmail(AuthService.getCurrentUser())
        }
    }

}