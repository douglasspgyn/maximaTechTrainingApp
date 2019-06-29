package douglasspgyn.com.github.maximatechtrainingapp.ui.updateprofile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import douglasspgyn.com.github.maximatechtrainingapp.R
import douglasspgyn.com.github.maximatechtrainingapp.application.Constant
import douglasspgyn.com.github.maximatechtrainingapp.extension.textString
import douglasspgyn.com.github.maximatechtrainingapp.model.User
import douglasspgyn.com.github.maximatechtrainingapp.model.UserUpdate
import kotlinx.android.synthetic.main.activity_update_profile.*

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

    }
}