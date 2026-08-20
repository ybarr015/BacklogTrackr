package com.x3vo.backlogtrackr.network.utils

import io.ktor.client.plugins.ClientRequestException
import kotlinx.io.IOException

//generic resource return type, template T is the data that will be part of success object
sealed interface Resource<out T> {
    // if Loading or Error there is no data so use Nothing type because Nothing is a subclass to use
    // for compiler that can be used for type safety. Basically tells the compiler Resource<Nothing>
    // can also be used as Resource<T> without writing extra code, so everywhere Resource is used
    // we tell it to use Resource<T> where T is the data that is being returned from the api. But in
    // the cases where its loading state or error state, Resource<Nothing> is also fine
    object Loading: Resource<Nothing>
    data class Error(val message: String): Resource<Nothing>

    //if success then there is data from the api call, so the data type will come from the api request return type
    data class Success<out T>(val data: T): Resource<T>
}

suspend fun <T> safeApiCall(apiRequest: suspend () -> T): Resource<T> {
    try {
        //try the api call and if successful set the resulting data type as the template type for the ApiResource
        val apiResult = apiRequest.invoke()
        return Resource.Success(apiResult)
    } catch (e: ClientRequestException) { //otherwise catch some exception and use error message for Api Resource
        val responseCode = e.response.status.value
        val message = when(responseCode) {
            401 -> "Expired Token" //todo: if here then the token is expired on the server, so need to actually do login again
            403 -> "Bad Oauth request" //todo: something very wrong
            429 -> "Rate Limit" //todo: maybe add a way to retry call on a rate limit error
            else -> "Other" // json error or some kind of other error
        }
        return Resource.Error(message)
    } catch (e: IOException) {
        return Resource.Error("IOException: error is ${e.message}")
    } catch (e: Exception) {
        return Resource.Error("Unknown Error: error is ${e.message}")
    }
}