package com.example.androidtd

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

//@Parcelize
//class Task constructor(id: String, title: String){
//    var id : String = id
//    var title : String= title
//    var description : String = ""
//
//    constructor(id: String, title: String, description: String) : this(id, title){
//        this.description=description
//    }
//}

//data class Task(val id: String, val title: String, val description: String?="") : Parcelable

@Parcelize
data class Task(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String
//    @Json(name = "due_date")
//    val due_date: String?
) : Parcelable
