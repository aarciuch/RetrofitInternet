package psm.lab.retrofitinternet.retrofit

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitVM(private val apiService: ApiService, application: Application) : AndroidViewModel(
    application) {

    private var _tableData = MutableStateFlow<List<String>>(emptyList())
    val tableData: StateFlow<List<String>> = _tableData

    private var _dbData = MutableStateFlow<Array<List<String>>>(emptyArray())
    val dbData: StateFlow<Array<List<String>>> = _dbData

    init {
        //fetchTableData()
    }
    
    fun fetchTableData() {
        apiService.getTable().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    _tableData.value = response.body() ?: emptyList()
                }
            }
            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.i("RETROFIT", "api getTable failure: ${t.message}")
            }
        })
    }

    fun fetchDbData() {
        apiService.getDB().enqueue(object : Callback<Array<List<String>>> {
            override fun onResponse(
                call: Call<Array<List<String>>>,
                response: Response<Array<List<String>>>,
            ) {
                if (response.isSuccessful) {
                    _dbData.value = response.body() ?: emptyArray()
                }
            }
            override fun onFailure(call: Call<Array<List<String>>>, t: Throwable) {
                Log.i("RETROFIT", "api getDB failure: ${t.message}")
            }
        })
    }

}