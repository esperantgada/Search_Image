package eg.esperantgada.searchimage.model

import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import eg.esperantgada.searchimage.repository.ImageRepository
import eg.esperantgada.searchimage.util.Constant.Companion.CURRENT_QUERY
import eg.esperantgada.searchimage.util.Constant.Companion.DEFAULT_QUERY
import javax.inject.Inject

/**
 * We injected the [Repository] in the [ViewModel] class
 * Using [SavedStateHandle] allows to handle [process] death
 */
@Suppress("KDocUnresolvedReference")

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repository: ImageRepository,
     state : SavedStateHandle) : ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    /**
     * This method will be called in the Fragment to get user's Query and set it to the current Query
     */
    fun setQuery(query : String){
        currentQuery.value = query
    }


    /**
     * [switchMap] is called here to switch from one query to a new query that is passed in
     * [getSearchResult] method in the repository in order to update data accordingly
     * Calling [cachedIn] method allows to handle app crashing when rotating device
     */
    val photosList = currentQuery.switchMap { newQuery ->
        repository.getSearchResult(newQuery).cachedIn(viewModelScope)
    }
}