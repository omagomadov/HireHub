package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.database.repository.DeveloperRepository
import kotlinx.coroutines.launch

class DeveloperViewModel(
    private val database: DeveloperRepository,
    developer: DeveloperDto?,
    application: Application
) : AndroidViewModel(application) {

    private var _currentDeveloper = MutableLiveData<DeveloperDto?>()
    val currentDeveloper: LiveData<DeveloperDto?>
        get() = _currentDeveloper

    init {
        if(developer != null) {
            _currentDeveloper.value = developer
        }
    }

}