package douglasspgyn.com.github.maximatechtrainingapp.network

import douglasspgyn.com.github.maximatechtrainingapp.model.BreedImage
import retrofit2.Call
import retrofit2.http.GET

interface BreedRoute {

    @GET("breeds/image/random")
    fun getRandomBreedImage(): Call<BreedImage>
}