package psm.lab.retrofitinternet.retrofit

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitVM(private val apiService: ApiService, application: Application) : AndroidViewModel(
    application) {

    private var _tableData = MutableStateFlow<List<String>>(emptyList())
    val tableData: MutableStateFlow<List<String>> = _tableData

    private var _dbData = MutableStateFlow<Array<List<String>>>(emptyArray())
    val dbData: MutableStateFlow<Array<List<String>>> = _dbData

    private var _osobyData = MutableStateFlow<Array<List<String>>>(emptyArray())
    val osobyData : MutableStateFlow<Array<List<String>>> = _osobyData

    fun clearList(listIndex : Int) {
        when(listIndex) {
            0 -> tableData.value = emptyList()
            1 -> dbData.value = emptyArray()
            2 -> osobyData.value = emptyArray()
            else -> {
                tableData.value = emptyList()
                dbData.value = emptyArray()
                osobyData.value = emptyArray()

            }
        }
    }

    init {
        //fetchTableData()
    }
    
    fun fetchTableData() {
        viewModelScope.launch {
            apiService.getTable().enqueue(object : Callback<List<String>> {
                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                    if (response.isSuccessful) {
                        _tableData.value = response.body() ?: emptyList()
                    }
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Log.i("RETROFIT", "api getTable failure: ${t.message}")
                }
            })
        }
    }

    fun fetchDbData() {
        viewModelScope.launch {
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

    fun getOsoby() {
        viewModelScope.launch {
            apiService.getOsoby().enqueue(object : Callback<Array<List<String>>> {
                override fun onResponse(
                    call: Call<Array<List<String>>>,
                    response: Response<Array<List<String>>>,
                ) {
                    if (response.isSuccessful) {
                        _osobyData.value = response.body() ?: emptyArray()
                    }
                }

                override fun onFailure(call: Call<Array<List<String>>>, t: Throwable) {
                    Log.i("RETROFIT", "api getOsoby failure: ${t.message}")
                }
            })
        }
    }

    fun addOsoba(name : String) {
        viewModelScope.launch {
            try {
                val response = apiService.addOsobaPost(name)
                if (response.isSuccessful) {
                    Log.i("RETROFIT", "api addOsoba: ${name} success : osoba has added, ${response.code()} , ${response.message()}")
                }
                else {
                    Log.i("RETROFIT", "api addOsoba no success : ${response.code()} , ${response.message()}")
                }
            } catch (e: Exception) {
                Log.i("RETROFIT", "api addOsoba failure: ${e.message}")
            }
        }
    }

    fun deleteOsobaByName(name : String) {
        val a = viewModelScope.launch() {
            try {
                val response = apiService.deleteOsobaByNamePost(name)
                if (response.isSuccessful) {
                    Log.i("RETROFIT", "api deleteOsobaByName: ${name} success : osoba has deleted, ${response.code()} , ${response.message()}")
                }
                else {
                    Log.i("RETROFIT", "api deleteOsoba no success : ${response.code()} , ${response.message()}")
                }
            } catch (e: Exception) {
                Log.i("RETROFIT", "api deleteOsoba failure: ${e.message}")
            }
        }
    }

}