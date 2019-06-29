package douglasspgyn.com.github.maximatechtrainingapp.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import douglasspgyn.com.github.maximatechtrainingapp.R
import douglasspgyn.com.github.maximatechtrainingapp.extension.textString
import douglasspgyn.com.github.maximatechtrainingapp.persistence.TokenPersistence
import douglasspgyn.com.github.maximatechtrainingapp.ui.main.MainActivity
import douglasspgyn.com.github.maximatechtrainingapp.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_authentication.*

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
        login.setOnClickListener {
            authenticate(email.textString, password.textString)
        }

        register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun authenticate(email: String, password: String) {

    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}