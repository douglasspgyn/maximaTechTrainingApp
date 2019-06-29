package douglasspgyn.com.github.maximatechtrainingapp.ui.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.widget.Toast
import douglasspgyn.com.github.maximatechtrainingapp.R
import douglasspgyn.com.github.maximatechtrainingapp.extension.textString
import douglasspgyn.com.github.maximatechtrainingapp.model.User
import douglasspgyn.com.github.maximatechtrainingapp.network.Api
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register.setOnClickListener {
            if (validFields()) {
                registerUser(User(name = name.textString,
                        email = email.textString,
                        password = password.textString))
            }
        }
    }

    private fun validFields(): Boolean {
        tilName.error = null
        tilEmail.error = null
        tilPassword.error = null

        var valid = true

        if (name.textString.isEmpty()) {
            tilName.error = getString(R.string.empty_field)
            valid = false
        }

        if (email.textString.isEmpty()) {
            tilEmail.error = getString(R.string.empty_field)
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.textString).matches()) {
            tilEmail.error = getString(R.string.invalid_email)
            valid = false
        }

        if (password.textString.isEmpty()) {
            tilPassword.error = getString(R.string.empty_field)
            valid = false
        } else if (password.textString.length !in 6..16) {
            tilPassword.error = getString(R.string.invalid_password_length)
            valid = false
        }

        return valid
    }

    private fun registerUser(user: User) {
        Api.authenticationRoute
                .register(user)
                .enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@RegisterActivity, "User registered!", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(this@RegisterActivity, "Failed to register\n${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Toast.makeText(this@RegisterActivity, "Failed to register", Toast.LENGTH_SHORT).show()
                    }
                })
    }
}