package douglasspgyn.com.github.maximatechtrainingapp.persistence

import com.google.gson.Gson
import douglasspgyn.com.github.maximatechtrainingapp.application.App
import douglasspgyn.com.github.maximatechtrainingapp.application.Constant
import douglasspgyn.com.github.maximatechtrainingapp.model.Token

class TokenPersistence {

    private val gson: Gson = Gson()

    fun getToken(): Token? {
        return gson.fromJson(App.sharedPreferences.getString(Constant.TOKEN, ""), Token::class.java)
    }

    fun saveToken(token: Token): Token? {
        App.sharedPreferences.edit().putString(Constant.TOKEN, gson.toJson(token)).apply()
        return getToken()
    }

    fun deleteToken() {
        App.sharedPreferences.edit().remove(Constant.TOKEN).apply()
    }
}