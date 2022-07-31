package com.berkayyetis.campbooktesting.repo

import androidx.lifecycle.LiveData
import com.berkayyetis.campbooktesting.api.RetrofitAPI
import com.berkayyetis.campbooktesting.model.ImageResponse
import com.berkayyetis.campbooktesting.roomdb.Camp
import com.berkayyetis.campbooktesting.roomdb.CampDao
import com.berkayyetis.campbooktesting.util.Resource
import java.lang.Exception
import javax.inject.Inject

class CampRepository @Inject constructor(private val campDao: CampDao, private val retrofitAPI: RetrofitAPI) : CampRepositoryInterface  {
    override suspend fun insertCamp(camp: Camp) {
        campDao.insertCamp(camp)
    }

    override suspend fun deleteCamp(camp: Camp) {
        campDao.deleteCamp(camp)
    }

    override fun getCamp(): LiveData<List<Camp>> {
        return campDao.observeCamps()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
         return try {
            val response = retrofitAPI.imageSearch(imageString)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }
            else{
                    Resource.error("error",null)
            }
        }catch (e: Exception){
            Resource.error("No Data!",null)
        }
    }
}