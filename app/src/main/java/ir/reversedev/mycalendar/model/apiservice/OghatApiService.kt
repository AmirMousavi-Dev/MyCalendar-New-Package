package ir.reversedev.mycalendar.model.apiservice

import ir.reversedev.mycalendar.model.data.OghatEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface OghatApiService {
    @GET("/owghat/")
    suspend fun getOghat (
        @Query(value = "lat") lat :Double,
        @Query(value = "long") lon :Double
    ) : OghatEntity
}