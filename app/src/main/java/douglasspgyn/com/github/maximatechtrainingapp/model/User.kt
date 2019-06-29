package douglasspgyn.com.github.maximatechtrainingapp.model

import java.io.Serializable

data class User(val id: Long = 0,
                val name: String = "",
                val email: String = "",
                val password: String = "") : Serializable