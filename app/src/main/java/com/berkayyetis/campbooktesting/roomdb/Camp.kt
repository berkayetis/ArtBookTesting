package com.berkayyetis.campbooktesting.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "camps")
data class Camp(
    var name: String,
    var campCity : String,
    var year: Int,
    var imageUrl : String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)