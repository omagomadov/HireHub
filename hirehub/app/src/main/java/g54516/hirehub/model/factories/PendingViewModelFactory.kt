package g54516.hirehub.model.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import g54516.hirehub.database.repository.AppointmentRepository
import g54516.hirehub.model.viewmodel.PendingViewModel

class PendingViewModelFactory(
    private val database: AppointmentRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PendingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PendingViewModel(database, application) as T
        }
        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }

}