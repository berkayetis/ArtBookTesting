package com.berkayyetis.campbooktesting.repo

import androidx.lifecycle.LiveData
import com.berkayyetis.campbooktesting.model.ImageResponse
import com.berkayyetis.campbooktesting.roomdb.Camp
import com.berkayyetis.campbooktesting.util.Resource

interface CampRepositoryInterface {
    suspend fun insertCamp(camp : Camp)

    suspend fun deleteCamp(camp : Camp)

    fun getCamp() : LiveData<List<Camp>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>
}