package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.database.repository.AppointmentRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class AppointmentViewModel(
    private val database: AppointmentRepository,
    val developer: DeveloperDto?,
    application: Application
) : AndroidViewModel(application) {

    private var _appointmentDate = MutableLiveData<String>()
    val appointmentDate: LiveData<String>
        get() = _appointmentDate

    private var _userSelectedValidDate = MutableLiveData<Boolean>()
    val userSelectedValidDate: LiveData<Boolean>
        get() = _userSelectedValidDate

    init {
        _appointmentDate.value = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.FRENCH))
        _userSelectedValidDate.value = true
    }

    fun addAppointment(user_email: String, developer_email: String, date: Date) {
        if(userSelectedValidDate.value == true) {
            database.addAppointment(user_email, developer_email, date)
        }
    }

    fun updateDate(date: LocalDate) {
        _appointmentDate.value = date
            .format(DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.FRENCH))
        _userSelectedValidDate.value = date.isAfter(LocalDate.now())
    }

}