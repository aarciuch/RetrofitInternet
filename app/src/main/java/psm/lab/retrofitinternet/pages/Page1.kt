package psm.lab.retrofitinternet.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import psm.lab.retrofitinternet.retrofit.RetrofitVM

@Composable
fun Page1(retrovitRetrofitVM: RetrofitVM = koinViewModel()) {
    val tableData = retrovitRetrofitVM.tableData.collectAsState()
    val dbData = retrovitRetrofitVM.dbData.collectAsState()
    val osobyData = retrovitRetrofitVM.osobyData.collectAsState()

    var osobaName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        retrovitRetrofitVM.getOsoby()
        retrovitRetrofitVM.fetchDbData()
        retrovitRetrofitVM.fetchTableData()
    }

    LazyColumn {
        item {
            HorizontalDivider(thickness = 5.dp)
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly)
            {
                Button(onClick = { retrovitRetrofitVM.fetchTableData() })
                {
                    Text("Retrofit getTable")
                }
                Button(onClick = { retrovitRetrofitVM.clearList(0)}) {
                    Text("Clear")
                }
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
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly)
            {
                Button(onClick = { retrovitRetrofitVM.fetchDbData() }) {
                    Text("Retrofit getDB")
                }
                Button(onClick = { retrovitRetrofitVM.clearList(1) }) {
                    Text("Clear")
                }
            }
        }
        item() {
            Text(text = "DB Data:", fontSize = 30.sp)
        }
        items(dbData.value) { item ->
            Text("${item}")
        }
        item {
            HorizontalDivider(thickness = 5.dp)
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly)
            {
                Button(onClick = { retrovitRetrofitVM.getOsoby() }) {
                    Text("Retrofit getOsoby")
                }
                Button(onClick = { retrovitRetrofitVM.clearList(2) }) {
                    Text("Clear")
                }
            }
        }
        item() {
            Text(text = "Osoby Data:", fontSize = 30.sp)
        }
        items(osobyData.value) { item ->
            Text(text = "${item[0]}",
                modifier = Modifier.clickable {
                    retrovitRetrofitVM.deleteOsobaByName(item[0])
                    retrovitRetrofitVM.getOsoby()
                })
        }
        item {
            HorizontalDivider(thickness = 5.dp)
        }
        item {
            Column (modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { retrovitRetrofitVM.addOsoba(osobaName)
                osobaName = ""
                retrovitRetrofitVM.getOsoby()}) {
                    Text("Add osoba")
                }
                OutlinedTextField(
                    value = osobaName,
                    onValueChange = {osobaName = it},
                    label = {Text("Osoba name")},
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
        }
    }
}