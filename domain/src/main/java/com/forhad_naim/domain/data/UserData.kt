package com.forhad_naim.domain.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserData(
    @SerializedName("dateToDayId")
    var dateToDayId: HashMap<String, Int>,
    @SerializedName("mealIdToDayId")
    var mealIdToDayId: HashMap<String, Int>
) : Serializable