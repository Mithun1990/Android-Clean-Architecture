package com.forhad_naim.domain.interfaceImpl


import com.forhad_naim.domain.interface_.UserEngagementByStatus
import com.forhad_naim.domain.utils.DomainConstants
import com.google.gson.Gson
import org.jetbrains.annotations.NotNull
import org.json.JSONObject
import javax.inject.Inject

class UserEngagementByStatusImpl @Inject constructor(
    @NotNull private val gsn: Gson
) :
    UserEngagementByStatus(gsn) {
    override fun activeUser(
        fromDate: String,
        toDate: String, userToDayAndMealInfo: HashMap<String, JSONObject>
    ): MutableList<String> {
        var userList = mutableListOf<String>()
        var keys = userToDayAndMealInfo.keys.iterator()
        while (keys.hasNext()) {
            val value = keys.next()
            var numOfMeal = userToDayAndMealInfo.get(value)?.let {
                return@let super.mealCount(
                    fromDate,
                    toDate,
                    it
                )
            }
            numOfMeal?.let {
                if (numOfMeal >= DomainConstants.ACTIVE_THRESHOLD) {
                    userList.add(value)
                }
            }
        }
        return userList
    }

    override fun superActiveUser(
        fromDate: String,
        toDate: String, userToDayAndMealInfo: HashMap<String, JSONObject>
    ): MutableList<String> {
        var userList = mutableListOf<String>()
        var keys = userToDayAndMealInfo.keys.iterator()
        while (keys.hasNext()) {
            val value = keys.next()
            var numOfMeal = userToDayAndMealInfo.get(value)?.let {
                return@let super.mealCount(
                    fromDate,
                    toDate,
                    it
                )
            }
            numOfMeal?.let {
                if (numOfMeal >= DomainConstants.SUPER_ACTIVE_THRESHOLD) {
                    userList.add(value)
                }
            }
        }
        return userList
    }

    override fun boredUser(
        fromDate: String,
        toDate: String, userToDayAndMealInfo: HashMap<String, JSONObject>
    ): MutableList<String> {
        var userList = mutableListOf<String>()
        var keys = userToDayAndMealInfo.keys.iterator()
        while (keys.hasNext()) {
            val value = keys.next()
            var isBored = userToDayAndMealInfo.get(value)?.let {
                return@let super.isBored(
                    fromDate,
                    toDate,
                    it
                )
            }
            isBored?.let {
                if (it)
                    userList.add(value)
            }
        }
        return userList
    }
}