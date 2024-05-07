package com.example.miproyecto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.miproyecto.databinding.ActivityAddUniversityBinding

class AddUniversityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_university)

        // Configura el clic del botón agregar universidad
        val buttonAddUniversity: Button = findViewById(R.id.buttonAddUniversity)
        buttonAddUniversity.setOnClickListener {
            // Recopila los datos del formulario
            val name = findViewById<EditText>(R.id.editTextName).text.toString()
            // Recopila los demás datos según sea necesario

            // Crea una nueva instancia de University con los datos recopilados
            val university = University(
                name = name,
                // Completa con los demás datos
            )

            // Agrega la nueva universidad a la lista de universidades (implementación específica depende de tu código)
            // Por ejemplo, puedes enviar la universidad de vuelta a la actividad principal y agregarla a la lista allí
            val intent = Intent()
            intent.putExtra("NEW_UNIVERSITY", university)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}


