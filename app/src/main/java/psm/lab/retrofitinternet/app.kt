package psm.lab.retrofitinternet

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class app : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@app)
            modules(moduleApp)
        }
    }
}