package psm.lab.retrofitinternet.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @GET("static/json/table.json")
    fun getTable() : Call<List<String>>

    @GET("getOsoby")
    fun getOsoby() : Call<Array<List<String>>>

    @GET("db")
    fun getDB() : Call<Array<List<String>>>

    @FormUrlEncoded
    @POST("addOsoba")
    @Headers("X-HTTP-Method-Override: PUT")
    suspend fun addOsobaPost(@Field("name") name: String): Response<Void>

    @FormUrlEncoded
    @POST("deleteOsoba")
    @Headers("X-HTTP-Method-Override: PUT")
    suspend fun deleteOsobaByNamePost(@Field("name") name: String) : Response<Void>


    companion object {
        val BASE_URL = "https://10.7.38.161:5000/"
    }

}