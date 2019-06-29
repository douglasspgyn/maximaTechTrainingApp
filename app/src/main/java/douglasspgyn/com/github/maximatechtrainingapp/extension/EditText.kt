package douglasspgyn.com.github.maximatechtrainingapp.extension

import android.widget.EditText

var EditText.textString: String
    get() = this.text.toString()
    set(value) = this.setText(value)