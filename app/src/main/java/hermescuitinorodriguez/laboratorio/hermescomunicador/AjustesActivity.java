package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class AjustesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        Intent intent = getIntent();
        String alumno = intent.getStringExtra("alumno");
        TextView textView = (TextView) findViewById(R.id.datos_alumno);
        textView.setText(alumno);

    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkbox_pista:
                if (checked)
                // agregar pestaña pista
                break;
            case R.id.checkbox_establo:
                if (checked)
                // agregar pestaña establo
                break;
            case R.id.checkbox_necesidades:
                if (checked)
                // agregar pestaña necesidades
                break;
            case R.id.checkbox_emociones:
                if (checked)
                // agregar pestaña emociones
                break;
        }
    }
}
