package douglasspgyn.com.github.maximatechtrainingapp.network

import douglasspgyn.com.github.maximatechtrainingapp.persistence.TokenPersistence
import okhttp3.Interceptor
import okhttp3.OkHttpClient

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
}