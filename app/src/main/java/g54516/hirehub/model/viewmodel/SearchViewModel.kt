package g54516.hirehub.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.database.repository.DeveloperRepository
import g54516.hirehub.model.FilterType
import kotlinx.coroutines.launch

class SearchViewModel(
    private val database: DeveloperRepository,
    application: Application
) : AndroidViewModel(application) {


    private var _developers = MutableLiveData<List<DeveloperDto>>()
    val developers: LiveData<List<DeveloperDto>>
        get() = _developers

    private var _filters = MutableLiveData<List<FilterType>>()
    val filters: LiveData<List<FilterType>>
        get() = _filters

    private var _developersOrdered = MutableLiveData<List<DeveloperDto>>()
    val developersOrdered: LiveData<List<DeveloperDto>>
        get() = _developersOrdered

    init {
        viewModelScope.launch {
            _developers.value = database.getAllDevelopers()
            _developersOrdered.value = database.getDevelopersOrderedByRating()
        }
        _filters.value = listOf(
            FilterType.BY_EXPERIENCE,
            FilterType.BY_RATING,
            FilterType.BY_FIRSTNAME,
            FilterType.BY_LASTNAME
        )
    }

    fun update(type: FilterType) {
        when (type) {
            FilterType.BY_RATING -> _developers.value = _developers.value?.sortedBy { it.rating }
            FilterType.BY_EXPERIENCE -> _developers.value =
                _developers.value?.sortedBy { it.experience_level }

            FilterType.BY_FIRSTNAME -> _developers.value =
                _developers.value?.sortedBy { it.firstName }

            FilterType.BY_LASTNAME -> _developers.value =
                _developers.value?.sortedBy { it.lastName }
        }
    }

}