package douglasspgyn.com.github.maximatechtrainingapp.ui.updateprofile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.widget.Toast
import douglasspgyn.com.github.maximatechtrainingapp.R
import douglasspgyn.com.github.maximatechtrainingapp.application.Constant
import douglasspgyn.com.github.maximatechtrainingapp.extension.textString
import douglasspgyn.com.github.maximatechtrainingapp.model.User
import douglasspgyn.com.github.maximatechtrainingapp.model.UserUpdate
import douglasspgyn.com.github.maximatechtrainingapp.network.Api
import kotlinx.android.synthetic.main.activity_update_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        intent.extras?.getSerializable(Constant.USER)?.let {
            user = it as User

            userProfileInfo.text = "${user?.id}\n${user?.name}\n${user?.email}"
        } ?: finish()



        update.setOnClickListener {
            if (validFields()) {
                val userUpdate = UserUpdate(getStringOrNull(name.textString),
                        getStringOrNull(email.textString),
                        getStringOrNull(password.textString),
                        getStringOrNull(newPassword.textString))

                updateUser(userUpdate)
            }
        }
    }

    private fun validFields(): Boolean {
        tilName.error = null
        tilEmail.error = null
        tilPassword.error = null
        tilNewPassword.error = null

        var valid = true

        if (user.email == email.textString) {
            tilEmail.error = getString(R.string.invalid_email_equals)
            valid = false
        } else if (email.textString.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email.textString).matches()) {
            tilEmail.error = getString(R.string.invalid_email)
            valid = false
        } else if (email.textString.isNotEmpty()) {
            if (password.textString.isEmpty()) {
                tilPassword.error = getString(R.string.email_missing_password)
                valid = false
            } else if (password.textString.length !in 6..16) {
                tilPassword.error = getString(R.string.invalid_password_length)
                valid = false
            }
        }

        if (newPassword.textString.isNotEmpty()) {
            when {
                newPassword.textString.length !in 6..16 -> {
                    tilNewPassword.error = getString(R.string.invalid_password_length)
                    valid = false
                }
                password.textString.isEmpty() -> {
                    tilPassword.error = getString(R.string.new_password_missing_password)
                    valid = false
                }
                password.textString.length !in 6..16 -> {
                    tilPassword.error = getString(R.string.invalid_password_length)
                    valid = false
                }
            }
        }

        return valid
    }

    private fun getStringOrNull(text: String): String? {
        return if (text.isEmpty()) {
            null
        } else {
            text
        }
    }

    private fun updateUser(userUpdate: UserUpdate) {
        Api.userRoute
                .update(user.id, userUpdate)
                .enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@UpdateProfileActivity, "Profile updated!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@UpdateProfileActivity, "Failed to update profile\n${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Toast.makeText(this@UpdateProfileActivity, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                })
    }
}