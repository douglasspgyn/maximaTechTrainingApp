package douglasspgyn.com.github.maximatechtrainingapp.model

import com.google.gson.annotations.SerializedName

data class UserUpdate(val name: String? = null,
                      val email: String? = null,
                      val password: String? = null,
                      @SerializedName("old_password") val oldPassword: String? = null)