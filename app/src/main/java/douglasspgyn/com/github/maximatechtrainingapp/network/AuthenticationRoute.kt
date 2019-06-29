package douglasspgyn.com.github.maximatechtrainingapp.network

import douglasspgyn.com.github.maximatechtrainingapp.model.Token
import douglasspgyn.com.github.maximatechtrainingapp.model.User
import retrofit2.Call
import retrofit2.http.*

interface AuthenticationRoute {

    @FormUrlEncoded
    @Headers("Authorization: Basic YW5kcm9pZDpFdFVOakV3dDlnQVk2YmVm")
    @POST("/oauth/token")
    fun authenticate(@Field("email") email: String, @Field("password") password: String, @Field("grant_type") grantType: String): Call<Token>

    @FormUrlEncoded
    @Headers("Authorization: Basic YW5kcm9pZDpFdFVOakV3dDlnQVk2YmVm")
    @POST("/oauth/token")
    fun refreshToken(@Field("refresh_token") token: String, @Field("grant_type") grantType: String): Call<Token>

    @Headers("Authorization: Basic YW5kcm9pZDpFdFVOakV3dDlnQVk2YmVm")
    @POST("/oauth/register")
    fun register(@Body user: User): Call<Unit>
}