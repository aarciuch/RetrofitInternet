package psm.lab.retrofitinternet

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import psm.lab.retrofitinternet.retrofit.ApiService
import psm.lab.retrofitinternet.retrofit.ApiService.Companion.BASE_URL
import psm.lab.retrofitinternet.retrofit.RetrofitVM
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val moduleApp = module {
    //singleOf(::provideRetrofit)
    //singleOf(::provideApiService)
    //viewModelOf(::RetrofitVM)
    single { provideRetrofit() }
    single { provideApiService(get()) }
    viewModel { RetrofitVM(get(), get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}