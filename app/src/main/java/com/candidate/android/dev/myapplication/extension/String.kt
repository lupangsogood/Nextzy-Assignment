package com.candidate.android.dev.myapplication.extension

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun String.isJsonFormat(): Boolean {
    try {
        JSONObject(this)
    } catch (ex: JSONException) {
        // edited, to include @Arthur's comment
        // e.g. in case JSONArray is valid as well...
        try {
            JSONArray(this)
        } catch (ex1: JSONException) {
            return false
        }
    }
    return true
}