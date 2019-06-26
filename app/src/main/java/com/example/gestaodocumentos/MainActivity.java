package com.example.gestaodocumentos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button BTNAvancar = (Button) findViewById(R.id.BTNAvancar);
        BTNAvancar.setOnClickListener(clicknext);
    }

    private View.OnClickListener clicknext = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent situationIntent = new Intent(MainActivity.this, DocumentoActivity.class);

            EditText nome = (EditText) findViewById(R.id.PTNome);
            String txtNome = nome.getText().toString();

            EditText matricula = (EditText) findViewById(R.id.PTMatricula);
            String txtMatricula = matricula.getText().toString();

            if (!txtNome.isEmpty() && !txtMatricula.isEmpty()) {
                Bundle bundle = new Bundle();

                bundle.putString("nome", txtNome);
                bundle.putString("matricula", txtMatricula);

                situationIntent.putExtras(bundle);
                startActivity(situationIntent);

                finish();
            }else if(txtNome.isEmpty()){
                Toast.makeText(getApplicationContext(), "Campo 'Nome' está vazio!", Toast.LENGTH_SHORT).show();
            }else if(txtMatricula.isEmpty()){
                Toast.makeText(getApplicationContext(), "Campo 'Matrícula' está vazio!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Os campos estam vazios!", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
