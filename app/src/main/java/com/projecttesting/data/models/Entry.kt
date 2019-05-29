package com.projecttesting.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 *  <a href="https://github.com/square/moshi#codegen">@JsonClass - Moshi’s Kotlin codegen support is an annotation processor</a>
 */
@Entity(tableName = "Entry")
@JsonClass(generateAdapter = true)
data class Entry(
    @PrimaryKey @ColumnInfo(name = "id") @Json(name = "id") var id: String,
    @ColumnInfo(name = "title") @Json(name = "title") var title: String?,
    @ColumnInfo(name = "author") @Json(name = "author") var author: String?,
    @ColumnInfo(name = "thumbnail") @Json(name = "thumbnail") var thumbnail: String?,
    @ColumnInfo(name = "comments") @Json(name = "num_comments") var comments: Int?,
    @ColumnInfo(name = "created") @Json(name = "created") var created: Long?,
    @ColumnInfo(name = "readed") var readed: Boolean = false

)