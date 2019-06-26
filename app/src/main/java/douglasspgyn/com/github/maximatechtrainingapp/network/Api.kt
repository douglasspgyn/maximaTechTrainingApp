package douglasspgyn.com.github.maximatechtrainingapp.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private const val API_URL: String = "https://dog.ceo/api/"

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    private fun <T> provideService(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    val breedRoute: BreedRoute = provideService(BreedRoute::class.java)
}