package com.example.justeatsample.ui.activities.main


import androidx.lifecycle.ViewModel
import com.example.justeatsample.data.source.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityVm @Inject constructor(private val repository: Repository) : ViewModel() {


}





