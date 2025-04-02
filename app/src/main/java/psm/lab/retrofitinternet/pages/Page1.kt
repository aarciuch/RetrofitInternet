package psm.lab.retrofitinternet.pages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import psm.lab.retrofitinternet.retrofit.RetrofitVM

@Composable
fun Page1(retrovitRetrofitVM: RetrofitVM = koinViewModel()) {
    val tableData = retrovitRetrofitVM.tableData.collectAsState()
    val dbData = retrovitRetrofitVM.dbData.collectAsState()

    LazyColumn {
        item {
            HorizontalDivider(thickness = 5.dp)
        }
        item {
            Button(onClick = {retrovitRetrofitVM.fetchTableData()}) {
                Text("Retrofit getTable")
            }
        }
        item() {
            Text(text = "Table Data:", fontSize = 30.sp)
        }
        items(tableData.value) { item ->
            Text("$item")
        }
        item {
            HorizontalDivider(thickness = 5.dp)
        }
        item {
            Button(onClick = {retrovitRetrofitVM.fetchDbData()}) {
                Text("Retrofit getDB")
            }
        }
        item() {
            Text(text = "DB Data:", fontSize = 30.sp)
        }
        items(dbData.value) { item ->
            Text("$item")
        }
        item {
            HorizontalDivider(thickness = 5.dp)
        }
    }
}