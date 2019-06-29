package douglasspgyn.com.github.maximatechtrainingapp.ui.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import douglasspgyn.com.github.maximatechtrainingapp.R
import douglasspgyn.com.github.maximatechtrainingapp.extension.textString
import douglasspgyn.com.github.maximatechtrainingapp.model.User
import kotlinx.android.synthetic.main.activity_register.*

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

    }
}