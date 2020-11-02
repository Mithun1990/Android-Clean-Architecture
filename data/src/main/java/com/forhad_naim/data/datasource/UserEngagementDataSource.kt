package com.forhad_naim.data.datasource

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import com.forhad_naim.data.utlis.DataConstants
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.io.InputStream

class UserEngagementDataSource constructor(private val assetManager: AssetManager) {
    fun getUserData(): HashMap<String, JSONObject> {
        var userToDayAndMealInfo = hashMapOf<String, JSONObject>()
        try {
            for (list in assetManager.list(DataConstants.ASSET_SUB_DIRECTORY)!!) {
                // Log.d("DATA ", list)
                var jsonObject = getJsonFromAssets(list)
                var userId = list.split(".").get(0)
                (jsonObject is JSONObject).apply {
                    jsonObject?.let {
                        var json =
                            JSONObject(it.toString()).getJSONObject(DataConstants.CALENDAR_JSON_TAG)
                        var newJson = JSONObject().put(
                            DataConstants.DATE_TO_DAY_JSON_TAG,
                            json.getJSONObject(DataConstants.DATE_TO_DAY_JSON_TAG)
                        ).put(
                            DataConstants.MEAL_TO_DAY_JSON_TAG,
                            json.getJSONObject(DataConstants.MEAL_TO_DAY_JSON_TAG)
                        )
                        userToDayAndMealInfo.put(userId, newJson)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return userToDayAndMealInfo
    }

    fun getJsonFromAssets(
        fileName: String?
    ): Any? {
        try {
            val inputStream: InputStream = assetManager.open(
                DataConstants.ASSET_SUB_DIRECTORY + "/" + fileName
            )
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}