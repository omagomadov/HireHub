package g54516.hirehub.model.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import g54516.hirehub.database.repository.DeveloperRepository
import g54516.hirehub.model.viewmodel.DeveloperViewModel

class DeveloperViewModelFactory(
    private val database: DeveloperRepository,
    private val id: String?,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeveloperViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeveloperViewModel(database, id, application) as T
        }
        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }

}