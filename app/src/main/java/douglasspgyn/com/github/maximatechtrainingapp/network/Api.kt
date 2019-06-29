package douglasspgyn.com.github.maximatechtrainingapp.network

import com.google.gson.GsonBuilder
import douglasspgyn.com.github.maximatechtrainingapp.persistence.TokenPersistence
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private const val API_URL: String = "http://172.16.55.195:5000/"

    private fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()

            newBuilder.addHeader("Accept", "*/*")

            val token = TokenPersistence().getToken()
            if (token != null && !token.isExpired()) {
                newBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded")
                newBuilder.addHeader("Authorization", "${token.tokenType} ${token.accessToken}")
            }

            chain.proceed(newBuilder.build())
        })

        return builder.build()
    }

    private var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(provideOkHttpClient())
            .build()

    private fun <T> provideService(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    val authenticationRoute: AuthenticationRoute = provideService(AuthenticationRoute::class.java)
    val userRoute: UserRoute = provideService(UserRoute::class.java)
}