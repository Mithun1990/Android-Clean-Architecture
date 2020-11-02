package com.forhad_naim.domain.interface_

import android.util.Log
import com.forhad_naim.domain.data.UserData
import com.forhad_naim.domain.data.UserDataResponse
import com.forhad_naim.domain.utils.DomainConstants
import com.google.gson.Gson
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

abstract class UserEngagementByStatus(private val gson: Gson) {

    fun jsonToModel(jsonObject: JSONObject): UserData {
        //Log.d("DATA", jsonObject.toString())
        val userData: UserData =
            gson.fromJson(jsonObject.toString(), UserData::class.java)
        return userData
    }

    fun selectedDayAsListSpecifiedPeriod(
        from: String,
        to: String,
        hashMapDateToDay: HashMap<String, Int>
    ): MutableList<Int> {
        var simpleDateFormat = SimpleDateFormat(DomainConstants.DATE_FORMAT)
        var fromDateAsDate = simpleDateFormat.parse(from)
        var toDateAsDate = simpleDateFormat.parse(to)
        var iterator = hashMapDateToDay.keys.iterator()
        var dayList = mutableListOf<Int>()
        //Log.d("DATA ", hashMap.keys.toString())
        try {
            while (iterator.hasNext()) {
                var value = iterator.next()
                var keyAsDate = simpleDateFormat.parse(value)
                //Log.d("DATA ", iterator.next())
                if (fromDateAsDate.compareTo(keyAsDate) * keyAsDate.compareTo(toDateAsDate) >= 0) {
                    hashMapDateToDay.get(value)?.let { dayList.add(it) }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dayList
    }

    fun selectedDayAsListPrecedingPeriod(
        from: String,
        hashMapDateToDay: HashMap<String, Int>
    ): MutableList<Int> {
        var simpleDateFormat = SimpleDateFormat(DomainConstants.DATE_FORMAT)
        var fromDateAsDate = simpleDateFormat.parse(from)
        var iterator = hashMapDateToDay.keys.iterator()
        var dayList = mutableListOf<Int>()
        try {
            while (iterator.hasNext()) {
                val value = iterator.next()
                var keyAsDate = simpleDateFormat.parse(value)
                if (keyAsDate.before(fromDateAsDate)) {
                    hashMapDateToDay.get(value)?.let { dayList.add(it) }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dayList
    }

    fun numberOfMealOnThePeriod(
        hashMapDayToMeal: HashMap<String, Int>,
        dayList: MutableList<Int>
    ): Int {
        var numOfMeal = 0
        for (dayId in dayList) {
            numOfMeal += Collections.frequency(hashMapDayToMeal.values, dayId)
        }
        return numOfMeal
    }

    fun mealCount(
        fromDate: String,
        toDate: String,
        jsonObject: JSONObject
    ): Int {
        var userData = jsonToModel(jsonObject)
        var dayList = selectedDayAsListSpecifiedPeriod(fromDate, toDate, userData.dateToDayId)
        var numOfMeal = numberOfMealOnThePeriod(userData.dateToDayId, dayList)
        return numOfMeal
    }

    fun isBored(
        fromDate: String,
        toDate: String,
        jsonObject: JSONObject
    ): Boolean {
        var isBored = false
        var userData = jsonToModel(jsonObject)
        var dayList = selectedDayAsListSpecifiedPeriod(fromDate, toDate, userData.dateToDayId)
        var numOfMeal = numberOfMealOnThePeriod(userData.mealIdToDayId, dayList)
        //Log.d("DATA", dayList.toString().plus(" " + numOfMeal))
        var precedingDayList = selectedDayAsListPrecedingPeriod(fromDate, userData.dateToDayId)
        var numOfPrecedingMeal = numberOfMealOnThePeriod(userData.mealIdToDayId, precedingDayList)
        //Log.d("DATA PRED", precedingDayList.toString().plus(" " + numOfPrecedingMeal))
        if (numOfPrecedingMeal >= DomainConstants.ACTIVE_THRESHOLD) {
            if (numOfMeal < DomainConstants.ACTIVE_THRESHOLD) {
                isBored = true
            }
        }
        return isBored
    }

    abstract fun activeUser(
        fromDate: String,
        toDate: String, userToDayAndMealInfo: HashMap<String, JSONObject>
    ): MutableList<String>

    abstract fun superActiveUser(
        fromDate: String,
        toDate: String, userToDayAndMealInfo: HashMap<String, JSONObject>
    ): MutableList<String>

    abstract fun boredUser(
        fromDate: String,
        toDate: String, userToDayAndMealInfo: HashMap<String, JSONObject>
    ): MutableList<String>
}