package psm.lab.retrofitinternet.pages

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.koinViewModel
import psm.lab.retrofitinternet.MainActivity
import psm.lab.retrofitinternet.UDP.UdpVM

@Composable
fun Page2(udpVM: UdpVM = koinViewModel(), activity: MainActivity)  {
    val perm = android.Manifest.permission.ACCESS_FINE_LOCATION
    val hasPerm = udpVM.isPremissionGranted(perm)

    if (hasPerm) {
        Log.i("NETWORK", "ma uprawnienie $perm")
    } else {
        Log.i("NETWORK", "nie ma uprawnienia $perm")
        LaunchedEffect(Unit) {
            activity.requestPermissions(arrayOf(perm), 100)
        }
    }


}
