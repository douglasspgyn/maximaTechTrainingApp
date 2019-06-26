package douglasspgyn.com.github.maximatechtrainingapp.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.squareup.picasso.Picasso
import douglasspgyn.com.github.maximatechtrainingapp.R
import douglasspgyn.com.github.maximatechtrainingapp.model.BreedImage
import douglasspgyn.com.github.maximatechtrainingapp.network.Api
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        random.setOnClickListener {
            Api.breedRoute
                .getRandomBreedImage()
                .enqueue(object : Callback<BreedImage> {
                    override fun onResponse(call: Call<BreedImage>, response: Response<BreedImage>) {
                        if (response.isSuccessful) {
                            Picasso.get().load(response.body()?.message).into(dogImage)
                        } else {
                            Toast.makeText(this@MainActivity, "Failed to load the image", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<BreedImage>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Failed to load the image", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}
