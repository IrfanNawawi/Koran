package com.mockdroid.koran.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopHeadlineResponse(

    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: List<ArticlesItem>? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable