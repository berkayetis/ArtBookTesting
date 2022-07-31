package com.berkayyetis.campbooktesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.berkayyetis.campbooktesting.R
import com.berkayyetis.campbooktesting.api.RetrofitAPI
import com.berkayyetis.campbooktesting.repo.CampRepository
import com.berkayyetis.campbooktesting.repo.CampRepositoryInterface
import com.berkayyetis.campbooktesting.roomdb.Camp
import com.berkayyetis.campbooktesting.roomdb.CampDao
import com.berkayyetis.campbooktesting.roomdb.CampDatabase
import com.berkayyetis.campbooktesting.util.Util.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context)
    = Room.databaseBuilder(
        context, CampDatabase::class.java,"CampBookDB"
        ).build()



    @Singleton
    @Provides
    fun injectDao(database : CampDatabase) = database.campDao()


    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao: CampDao, api:RetrofitAPI)
    = CampRepository(dao,api) as CampRepositoryInterface



    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
}