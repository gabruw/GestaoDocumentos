package com.example.gestaodocumentos;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);

        Button BTNRetornar = (Button) findViewById(R.id.BTNRetornar2);
        Button BTNSair = (Button) findViewById(R.id.BTNSair);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        final String Nome = bundle.getString("nome");
        final String Matricula = bundle.getString("matricula");

        BTNRetornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDocumento = new Intent(ConfirmacaoActivity.this, DocumentoActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString("nome", Nome);
                bundle.putString("matricula", Matricula);

                intentDocumento.putExtras(bundle);
                startActivity(intentDocumento);

                finish();
            }
        });

        BTNSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
