package com.berkayyetis.campbooktesting.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Camp::class], version = 1)
abstract class CampDatabase : RoomDatabase(){
    abstract fun campDao() : CampDao

}