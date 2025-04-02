package psm.lab.retrofitinternet.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("static/json/table.json")
    fun getTable() : Call<List<String>>

    @GET("db")
    fun getDB() : Call<Array<List<String>>>

    companion object {
        val BASE_URL = "https://10.7.38.161:5000/"
    }

}