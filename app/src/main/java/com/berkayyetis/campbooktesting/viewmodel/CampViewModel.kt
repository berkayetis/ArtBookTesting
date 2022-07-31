package com.berkayyetis.campbooktesting.viewmodel

import android.media.Image
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkayyetis.campbooktesting.model.ImageResponse
import com.berkayyetis.campbooktesting.repo.CampRepository
import com.berkayyetis.campbooktesting.repo.CampRepositoryInterface
import com.berkayyetis.campbooktesting.roomdb.Camp
import com.berkayyetis.campbooktesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampViewModel @Inject constructor(private val repository: CampRepositoryInterface) :ViewModel() {

    //Camp Fragment
    val campList = repository.getCamp()

    //Image Api Fragment
    private val images = MutableLiveData<Resource<ImageResponse>>() //use Resource to follow process
    val imageList : LiveData<Resource<ImageResponse>>//To access from other classes
        get() = images

    private val selectedImage = MutableLiveData<String>() //get imgUrl
    val selectedImageUrl : LiveData<String> //To access from other classes
        get() = selectedImage

    // Camp details fragment
    private var insertCampMsg = MutableLiveData<Resource<Camp>>()
    val insertCampMessage : LiveData<Resource<Camp>>
       get() = insertCampMsg


    fun resetInsertCampMsg(){
        insertCampMsg = MutableLiveData<Resource<Camp>>()
    }

    fun setSelectedImage(url : String){
        selectedImage.postValue(url)
    }

    fun deleteCamp(camp: Camp){
        viewModelScope.launch {
            repository.deleteCamp(camp)
        }
    }
    fun makeCamp(name: String, artistName: String, year: String){
        if(name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertCampMsg.postValue(Resource.error("enter name, campName, year",null))
            return
        }
        val yearInt = try {
            year.toInt()
        }catch (e: Exception){
            insertCampMsg.postValue(Resource.error("Year should be number",null))
            return
        }
        val camp = Camp(name,artistName,yearInt,selectedImage.value?:"")
        insertCamp(camp)
        setSelectedImage("")
        insertCampMsg.postValue(Resource.success(camp))
    }
    fun insertCamp(camp: Camp){
        viewModelScope.launch {
            repository.insertCamp(camp)
        }
    }

    fun searchForImage(searchString: String){
        if(searchString.isEmpty()){
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }

}