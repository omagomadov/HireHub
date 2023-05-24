package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.database.dto.AppointmentDto
import g54516.hirehub.database.repository.AppointmentRepository
import g54516.hirehub.database.service.AuthService
import kotlinx.coroutines.launch

class PendingViewModel(
    val database: AppointmentRepository,
    application: Application
) : AndroidViewModel(application) {

    private var _appointments = MutableLiveData<List<AppointmentDto>>()
    val appointments: LiveData<List<AppointmentDto>>
        get() = _appointments

    init {
        fetchAppointments()
    }

    fun approveAppointment(appointment: AppointmentDto) {
        viewModelScope.launch {
            val documentId = database.getAppointmentId(appointment)
            database.approuveAppointment(documentId, appointment)
            fetchAppointments()
        }
    }

    fun disapprouveAppointment(appointment: AppointmentDto) {
        viewModelScope.launch {
            val documentId = database.getAppointmentId(appointment)
            database.disapprouveAppointment(documentId, appointment)
            fetchAppointments()
        }
    }

    private fun fetchAppointments() {
        viewModelScope.launch {
            _appointments.value = database.getAllPendingAppointments(AuthService.getCurrentUser())
        }
    }

}