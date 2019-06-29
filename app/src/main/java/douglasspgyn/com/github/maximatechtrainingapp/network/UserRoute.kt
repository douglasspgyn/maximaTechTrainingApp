package douglasspgyn.com.github.maximatechtrainingapp.network

import douglasspgyn.com.github.maximatechtrainingapp.model.User
import douglasspgyn.com.github.maximatechtrainingapp.model.UserUpdate
import retrofit2.Call
import retrofit2.http.*

interface UserRoute {

    @GET("/v1/users/profile")
    fun profile(): Call<User>

    @GET("/v1/users/{id}")
    fun user(@Path("id") id: Long): Call<User>

    @PUT("/v1/users/{id}")
    fun update(@Path("id") id: Long, @Body user: UserUpdate): Call<Unit>

    @DELETE("/v1/users/{id}")
    fun delete(@Path("id") id: Long): Call<Unit>
}