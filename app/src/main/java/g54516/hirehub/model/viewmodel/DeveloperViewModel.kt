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
    private val id: String?,
    application: Application
) : AndroidViewModel(application) {

    private var _developer = MutableLiveData<DeveloperDto>()
    val developer: LiveData<DeveloperDto>
        get() = _developer

    init {
        viewModelScope.launch {
            if (id != null) {
                _developer.value = database.getDeveloperByEmail(id)
            }
        }
    }

}