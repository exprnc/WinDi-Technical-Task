package com.exprnc.winditechnicaltask.data.datasource.remote.api.adapter

import com.exprnc.winditechnicaltask.core.ApiResponse
import com.exprnc.winditechnicaltask.core.ValidationError
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseAdapter<T>(
    private val successType: Type,
    private val gson: Gson
) : TypeAdapter<ApiResponse<T>>() {

    override fun write(out: JsonWriter?, value: ApiResponse<T>?) {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun read(jsonReader: JsonReader): ApiResponse<T> {
        val jsonElement = JsonParser.parseReader(jsonReader)
        return try {
            val successData = gson.fromJson<T>(jsonElement, successType)
            ApiResponse.Success(successData)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            val error = gson.fromJson(jsonElement, ValidationError::class.java)
            ApiResponse.Error(error)
        }
    }
}

class ApiResponseTypeAdapterFactory : TypeAdapterFactory {

    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType = type.rawType
        if (rawType == ApiResponse::class.java) {
            val parameterizedType = type.type as? ParameterizedType
            val successType = parameterizedType?.actualTypeArguments?.get(0)
            successType?.let {
                return ApiResponseAdapter<Any>(it, gson) as TypeAdapter<T>
            }
        }
        return null
    }
}