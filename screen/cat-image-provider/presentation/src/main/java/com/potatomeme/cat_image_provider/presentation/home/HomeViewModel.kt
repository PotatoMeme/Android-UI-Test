package com.potatomeme.cat_image_provider.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.potatomeme.cat_image_provider.domain.entity.CatEntity
import com.potatomeme.cat_image_provider.domain.usecase.RequestCatsUseCase
import com.potatomeme.cat_image_provider.domain.usecase.RequestPagingCatsUseCase
import com.potatomeme.cat_image_provider.presentation.mapper.PresentationToDomainMapper.toParcelable
import com.potatomeme.cat_image_provider.presentation.model.ParcelableCat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val requestCatsUseCase: RequestCatsUseCase,
    private val requestPagingCatsUseCase: RequestPagingCatsUseCase,
) : ViewModel() {
    //request
    private val _requestCats: MutableStateFlow<List<ParcelableCat>> = MutableStateFlow(emptyList())
    val requestCats: StateFlow<List<ParcelableCat>> = _requestCats.asStateFlow()

    //request - paging
    private val _requestPagingCats: MutableStateFlow<PagingData<CatEntity>> = MutableStateFlow(PagingData.empty())
    val requestPagingCats: StateFlow<PagingData<CatEntity>> = _requestPagingCats.asStateFlow()

    fun requestCats() = viewModelScope.launch {
        Result
        val result: Result<List<CatEntity>> = requestCatsUseCase(10, 0, "ASC")
        when {
            result.isSuccess -> {
                _requestCats.value = result.getOrNull()?.map {
                    it.toParcelable()
                } ?: emptyList()
            }

            result.isFailure -> {

            }
        }
    }

    fun requestPagingCats() = viewModelScope.launch {
        requestPagingCatsUseCase()
            .cachedIn(viewModelScope)
            .collect { catResult ->
                _requestPagingCats.value = catResult
            }

    }
}