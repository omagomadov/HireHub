package g54516.hirehub.model.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import g54516.hirehub.database.dao.UserDao
import g54516.hirehub.database.repository.AppointmentRepository
import g54516.hirehub.model.viewmodel.HomeViewModel

class HomeViewModelFactory(
    val database: AppointmentRepository,
    private val room: UserDao,
    private val isDeveloper: Boolean?,
    val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(database, room, isDeveloper, application) as T
        }
        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }

}