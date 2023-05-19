package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import g54516.hirehub.database.dto.DeveloperDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class AppointmentViewModel(
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

    fun updateDate(date: LocalDate) {
        _appointmentDate.value = date
            .format(DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.FRENCH))
        _userSelectedValidDate.value = date.isAfter(LocalDate.now())
    }

}