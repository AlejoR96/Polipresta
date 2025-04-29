package com.poli.polipresta.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poli.polipresta.data.FakeDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _currentDebt = MutableStateFlow(0.0)
    val currentDebt = _currentDebt.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _currentDebt.value = FakeDataSource.currentDebt
        }
    }
}