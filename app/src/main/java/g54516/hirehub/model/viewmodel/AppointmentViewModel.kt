package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.database.repository.AppointmentRepository
import java.time.LocalDate

class AppointmentViewModel(
    private val database: AppointmentRepository,
    val developer: DeveloperDto?,
    application: Application
) : AndroidViewModel(application) {

    private var _appointmentDate = MutableLiveData<LocalDate>()
    val appointmentDate: LiveData<LocalDate>
        get() = _appointmentDate

    private var _userSelectedValidDate = MutableLiveData<Boolean>()
    val userSelectedValidDate: LiveData<Boolean>
        get() = _userSelectedValidDate

    init {
        _appointmentDate.value = LocalDate.now()
        _userSelectedValidDate.value = true
    }

    fun addAppointment(user_email: String, developer: DeveloperDto, date: LocalDate?) {
        if (userSelectedValidDate.value == true) {
            if (date != null) {
                database.addAppointment(user_email, developer, date)
            }
        }
    }

    fun updateDate(date: LocalDate) {
        _appointmentDate.value = date
        _userSelectedValidDate.value = date.isAfter(LocalDate.now())
    }

}