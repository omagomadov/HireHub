package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.database.repository.DeveloperRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val database: DeveloperRepository,
    application: Application
) : AndroidViewModel(application) {


    private var _developers = MutableLiveData<List<DeveloperDto>>()
    val developers: LiveData<List<DeveloperDto>>
        get() = _developers

    private var _developersOrdered = MutableLiveData<List<DeveloperDto>>()
    val developersOrdered: LiveData<List<DeveloperDto>>
        get() = _developersOrdered

    init {
        viewModelScope.launch {
            _developers.value = database.getAllDevelopers()
            _developersOrdered.value = database.getDevelopersOrderedByRating()
        }
    }

}