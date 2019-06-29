package douglasspgyn.com.github.maximatechtrainingapp.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import douglasspgyn.com.github.maximatechtrainingapp.R
import douglasspgyn.com.github.maximatechtrainingapp.application.Constant
import douglasspgyn.com.github.maximatechtrainingapp.extension.textString
import douglasspgyn.com.github.maximatechtrainingapp.model.Token
import douglasspgyn.com.github.maximatechtrainingapp.network.Api
import douglasspgyn.com.github.maximatechtrainingapp.persistence.TokenPersistence
import douglasspgyn.com.github.maximatechtrainingapp.ui.main.MainActivity
import douglasspgyn.com.github.maximatechtrainingapp.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_authentication.*
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationActivity : AppCompatActivity() {

    private val tokenPersistence: TokenPersistence = TokenPersistence()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        if (tokenPersistence.getToken() != null) {
            goToMain()
        } else {
            setListeners()
        }
    }

    private fun setListeners() {
        register.setOnClickListener {

        }

        login.setOnClickListener {
            authenticate(email.textString, password.textString)
        }

        register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun x() {
        try {
        } catch (e: JSONException) {
            Log.d("JSONException", "Failed to parse json", e)
        }
    }

    private fun authenticate(email: String, password: String) {
        Api.authenticationRoute
                .authenticate(email, password, Constant.GRANT_TYPE_PASSWORD)
                .enqueue(object : Callback<Token> {
                    override fun onResponse(call: Call<Token>, response: Response<Token>) {
                        if (response.isSuccessful) {
                            tokenPersistence.saveToken(response.body()!!)
                            goToMain()
                        } else {
                            Toast.makeText(this@AuthenticationActivity, "${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Token>, t: Throwable) {
                        Toast.makeText(this@AuthenticationActivity, "Failed to login", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}