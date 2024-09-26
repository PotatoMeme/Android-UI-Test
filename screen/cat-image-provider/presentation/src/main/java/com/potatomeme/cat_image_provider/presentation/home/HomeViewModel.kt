package com.potatomeme.cat_image_provider.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.potatomeme.cat_image_provider.domain.entity.CatEntity
import com.potatomeme.cat_image_provider.domain.usecase.DeleteCatByIdUseCase
import com.potatomeme.cat_image_provider.domain.usecase.GetSavedCatsUseCase
import com.potatomeme.cat_image_provider.domain.usecase.InsertCatUseCase
import com.potatomeme.cat_image_provider.domain.usecase.RequestCatsUseCase
import com.potatomeme.cat_image_provider.domain.usecase.RequestPagingCatsUseCase
import com.potatomeme.cat_image_provider.presentation.mapper.PresentationToDomainMapper.toDomain
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
    private val getSavedCatsUseCase: GetSavedCatsUseCase,
    private val insertCatUseCase: InsertCatUseCase,
    private val deleteCatUseCase: DeleteCatByIdUseCase,
) : ViewModel() {
    //request
    private val _requestCats: MutableStateFlow<List<ParcelableCat>> = MutableStateFlow(emptyList())
    val requestCats: StateFlow<List<ParcelableCat>> = _requestCats.asStateFlow()

    //request - paging
    private val _requestPagingCats: MutableStateFlow<PagingData<CatEntity>> = MutableStateFlow(PagingData.empty())
    val requestPagingCats: StateFlow<PagingData<CatEntity>> = _requestPagingCats.asStateFlow()

    //saved - paging
    private val _getSavedCats: MutableStateFlow<PagingData<CatEntity>> = MutableStateFlow(PagingData.empty())
    val getSavedCats: StateFlow<PagingData<CatEntity>> = _getSavedCats.asStateFlow()

    init {
        requestPagingCats()
        getSavedCats()
    }

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

    fun insertCat(cat: ParcelableCat) = viewModelScope.launch {
        insertCatUseCase(cat.toDomain())
    }

    fun deleteCat(id: String) = viewModelScope.launch {
        deleteCatUseCase(id)
    }

    private fun requestPagingCats() = viewModelScope.launch {
        requestPagingCatsUseCase()
            .cachedIn(viewModelScope)
            .collect { catResult ->
                _requestPagingCats.value = catResult
            }
    }

    private fun getSavedCats() = viewModelScope.launch {
        getSavedCatsUseCase()
            .cachedIn(viewModelScope)
            .collect { catResult ->
                _getSavedCats.value = catResult

            }
    }

}