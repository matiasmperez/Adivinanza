package com.example.adivinanza;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private TextView textView;
    private Random random;
    private int numeroAleatorio;
    private int intentos;
    private int puntajeActual;
    private int maxPuntaje;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        random = new Random();
        numeroAleatorio = random.nextInt(5) + 1;
        intentos = 0;
        puntajeActual = 0;
        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        maxPuntaje = sharedPreferences.getInt("maxPuntaje", 0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoIngresado = editText.getText().toString();
                int numeroIngresado = Integer.parseInt(textoIngresado);

                if (numeroIngresado == numeroAleatorio) {
                    puntajeActual += 10;

                    if (puntajeActual > maxPuntaje) {
                        maxPuntaje = puntajeActual;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("maxPuntaje", maxPuntaje);
                        editor.apply();
                    }

                    textView.setText("¡Felicidades! Adivinaste el número.\n" +
                            "Puntaje actual: " + puntajeActual + "\n" +
                            "Máximo puntaje: " + maxPuntaje);

                    intentos = 0;
                    numeroAleatorio = random.nextInt(5) + 1;
                } else {
                    intentos++;

                    if (intentos == 5) {
                        puntajeActual = 0;
                        intentos = 0;
                        numeroAleatorio = random.nextInt(5) + 1;
                        textView.setText("Lo siento, has perdido. El número era " + numeroAleatorio + ".\n" +
                                "Puntaje actual: " + puntajeActual + "\n" +
                                "Máximo puntaje: " + maxPuntaje);
                    } else {
                        textView.setText("Lo siento, intenta de nuevo.\n" +
                                "Puntaje actual: " + puntajeActual + "\n" +
                                "Máximo puntaje: " + maxPuntaje);
                    }
                }

                editText.setText("");
            }
        });

        textView.setText("Puntaje actual: " + puntajeActual + "\n" +
                "Máximo puntaje: " + maxPuntaje);
    }
}
