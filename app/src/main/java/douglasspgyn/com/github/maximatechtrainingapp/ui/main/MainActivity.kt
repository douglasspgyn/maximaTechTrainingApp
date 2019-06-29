package douglasspgyn.com.github.maximatechtrainingapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import douglasspgyn.com.github.maximatechtrainingapp.R
import douglasspgyn.com.github.maximatechtrainingapp.application.Constant
import douglasspgyn.com.github.maximatechtrainingapp.extension.textString
import douglasspgyn.com.github.maximatechtrainingapp.model.Token
import douglasspgyn.com.github.maximatechtrainingapp.model.User
import douglasspgyn.com.github.maximatechtrainingapp.persistence.TokenPersistence
import douglasspgyn.com.github.maximatechtrainingapp.ui.authentication.AuthenticationActivity
import douglasspgyn.com.github.maximatechtrainingapp.ui.updateprofile.UpdateProfileActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val tokenPersistence: TokenPersistence = TokenPersistence()
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (tokenPersistence.getToken() == null) {
            openAuthentication()
        } else {
            setListeners()
        }
    }

    private fun setListeners() {
        getProfile.setOnClickListener {
            profileId.textString.toLongOrNull()?.let {
                getUserProfile(it)
            } ?: getCurrentUserProfile()
        }

        editProfile.setOnClickListener {
            startActivity(Intent(this, UpdateProfileActivity::class.java).apply { putExtra(Constant.USER, user) })
        }

        deleteProfile.setOnClickListener {
            Toast.makeText(this, "Long press to delete", Toast.LENGTH_SHORT).show()
        }

        deleteProfile.setOnLongClickListener {
            user?.let {
                deleteUser(it)
            } ?: let {
                Toast.makeText(this, "Get a profile before", Toast.LENGTH_SHORT).show()
            }

            true
        }

        checkToken.setOnClickListener {
            tokenPersistence.getToken()?.let {
                if (it.isExpired()) {
                    Toast.makeText(this, "Token expired", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Token ok", Toast.LENGTH_SHORT).show()
                }
            }
        }

        refreshToken.setOnClickListener {
            Toast.makeText(this, "Long press to refresh", Toast.LENGTH_SHORT).show()
        }

        refreshToken.setOnLongClickListener {
            refreshToken(tokenPersistence.getToken()!!)
            true
        }

        logout.setOnClickListener {
            openAuthentication()
        }
    }

    private fun getUserProfile(id: Long) {

    }

    private fun getCurrentUserProfile() {

    }

    private fun deleteUser(user: User) {

    }

    private fun refreshToken(token: Token) {

    }

    private fun openAuthentication() {
        tokenPersistence.deleteToken()
        startActivity(Intent(this, AuthenticationActivity::class.java))
        finish()
    }
}
