package com.example.gestaodocumentos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DocumentoActivity extends AppCompatActivity {

    String Matricula, Nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documento);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        // Spinner
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.SnTipo);

        String[] items = new String[]{"Selecione o tipo", "Nota Físcal", "Fiança", "Nota Promissória", "Duplicatas", "Recibo", "Aval", "Vales"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dynamicSpinner.setAdapter(adapter);

        // Buttons
        FloatingActionButton BTNCamera = (FloatingActionButton) findViewById(R.id.FACamera);
        Button BTNFinalizar = (Button) findViewById(R.id.BTNFinalizar);

        // Intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Nome = bundle.getString("nome");
        Matricula = bundle.getString("matricula");

        final TextView matricula = (TextView) findViewById(R.id.PTMatricula);
        matricula.setText(Matricula);

        final TextView nome = (TextView) findViewById(R.id.PTNome);
        nome.setText(Nome);

        // Camera
        BTNCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File photoDirectory = new File(Environment.getExternalStorageDirectory(), "Documentos");
                if (!photoDirectory.exists()) {
                    photoDirectory.mkdir();
                }

                String photoName = getPhotName();
                File imgFile = new File(photoDirectory, photoName);

                Uri uriPhoto = FileProvider.getUriForFile(DocumentoActivity.this, BuildConfig.APPLICATION_ID + ".provider", imgFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto);

                startActivityForResult(cameraIntent, 0);
            }
        });

        // Confirmação
        BTNFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentConfimacao = new Intent(DocumentoActivity.this, ConfirmacaoActivity.class);

                TextView origem = (TextView) findViewById(R.id.PTOrigem);
                String txtOrigem = origem.getText().toString();

                Spinner tipo = (Spinner) findViewById(R.id.SnTipo);
                String txtTipo = tipo.getSelectedItem().toString();

                String txtMatricula = matricula.getText().toString();
                String txtNome = nome.getText().toString();

                if (!txtOrigem.isEmpty() && !txtTipo.contains("Selecione") && !txtMatricula.isEmpty() && !txtNome.isEmpty()) {
                    Bundle bundle = new Bundle();

                    bundle.putString("nome", txtNome);
                    bundle.putString("matricula", txtMatricula);

                    intentConfimacao.putExtras(bundle);
                    startActivity(intentConfimacao);

                    finish();
                } else if (txtTipo.contains("Selecione")) {
                    Toast.makeText(getApplicationContext(), "Selecione um 'Tipo'!", Toast.LENGTH_SHORT).show();
                } else if (txtOrigem.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo 'Origem' está vazio!", Toast.LENGTH_SHORT).show();
                } else if (txtMatricula.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo 'Matrícula' está vazio!", Toast.LENGTH_SHORT).show();
                } else if (txtNome.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo 'Nome' está vazio!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Campo 'Origem' e 'Tipo' estam vazios!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getPhotName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());

        return Matricula + "_" + Nome + "_" + timestamp + "_"  + ".jpg";
    }
}
