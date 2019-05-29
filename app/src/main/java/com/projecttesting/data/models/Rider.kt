package com.projecttesting.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 *  <a href="https://github.com/square/moshi#codegen">@JsonClass - Moshi’s Kotlin codegen support is an annotation processor</a>
 */
@Entity(tableName = "Riders")
@JsonClass(generateAdapter = true)
data class Rider(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int?,
    @ColumnInfo(name = "title") @Json(name = "title") var title: String?,
    @ColumnInfo(name = "firstName") @Json(name = "first_name") var firstName: String?,
    @ColumnInfo(name = "lastName") @Json(name = "last_name") var lastName: String?,
    @ColumnInfo(name = "email") @Json(name = "email") var email: String?,
    @ColumnInfo(name = "type") @Json(name = "type") var type: String?,  // currently only rider
    @ColumnInfo(name = "score") @Json(name = "score") var score: Int?
)