package douglasspgyn.com.github.maximatechtrainingapp.application

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

class App : Application() {

    companion object {
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()

        initSharedPreferences()
    }

    private fun initSharedPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    }
}