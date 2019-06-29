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
import douglasspgyn.com.github.maximatechtrainingapp.network.Api
import douglasspgyn.com.github.maximatechtrainingapp.persistence.TokenPersistence
import douglasspgyn.com.github.maximatechtrainingapp.ui.authentication.AuthenticationActivity
import douglasspgyn.com.github.maximatechtrainingapp.ui.updateprofile.UpdateProfileActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        Api.userRoute
                .user(id)
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            user = response.body()
                            userProfileInfo.text = "${user?.id}\n${user?.name}\n${user?.email}"
                            Toast.makeText(this@MainActivity, "Profile loaded", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Failed to load profile", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun getCurrentUserProfile() {
        Api.userRoute
                .profile()
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            user = response.body()
                            userProfileInfo.text = "${user?.id}\n${user?.name}\n${user?.email}"
                            Toast.makeText(this@MainActivity, "Profile loaded", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Failed to load profile", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun deleteUser(user: User) {
        Api.userRoute
                .delete(user.id)
                .enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@MainActivity, "User deleted", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "Failed to delete user\n${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Failed to delete user", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun refreshToken(token: Token) {
        Api.authenticationRoute
                .refreshToken(token.refreshToken, Constant.GRANT_TYPE_REFRESH_TOKEN)
                .enqueue(object : Callback<Token> {
                    override fun onResponse(call: Call<Token>, response: Response<Token>) {
                        if (response.isSuccessful) {
                            tokenPersistence.saveToken(response.body()!!)
                            Toast.makeText(this@MainActivity, "Token refreshed", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "Failed to refresh token\n${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Token>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Failed to refresh token", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun openAuthentication() {
        tokenPersistence.deleteToken()
        startActivity(Intent(this, AuthenticationActivity::class.java))
        finish()
    }
}
