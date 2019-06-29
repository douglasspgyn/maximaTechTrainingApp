package douglasspgyn.com.github.maximatechtrainingapp.model

import com.google.gson.annotations.SerializedName

data class Token(@SerializedName("token_type") val tokenType: String,
                 @SerializedName("access_token") val accessToken: String,
                 @SerializedName("refresh_token") val refreshToken: String,
                 @SerializedName("expires_at") val expiresAt: Long) {

    fun isExpired(): Boolean = expiresAt <= System.currentTimeMillis()
}