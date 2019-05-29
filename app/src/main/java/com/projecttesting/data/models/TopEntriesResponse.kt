package com.projecttesting.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by alejandroperez on 2019-05-29.
 */

@JsonClass(generateAdapter = true)
data class TopEntriesResponse(

    @Json(name = "data") var data: TopEntriesDataResponse?
)

@JsonClass(generateAdapter = true)
data class TopEntriesDataResponse(

    @Json(name = "dist") var dist: Int?,
    @Json(name = "children") var children: Collection<TopEntriesDataChildrenResponse>?,
    @Json(name = "after") var after: String?,
    @Json(name = "before") var before: String?

)

@JsonClass(generateAdapter = true)
data class TopEntriesDataChildrenResponse(

    @Json(name = "data") var data: Entry?
)