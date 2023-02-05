package ir.reversedev.mycalendar.model.apiservice

import ir.reversedev.mycalendar.model.data.MonasebatEntity
import retrofit2.http.GET

interface MonasebatApiService {
    @GET(value = "/monasebat/")
    suspend fun getMonasebat () : MonasebatEntity
}