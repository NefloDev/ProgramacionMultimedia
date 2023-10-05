package com.example.juegoadivinapalabra;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ModifyWordsActivity extends Activity {
    private Button volverButton;

    private Button addButton;

    private Button saveButton;

    private EditText inputText;

    private ListView wordList;

    private ArrayAdapter<String> myAdapter;

    private String text;

    public static final Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_words_activity);

        volverButton = (Button) findViewById(R.id.volverButtonModif);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchMainActivity();
            }
        });

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainActivity().saveWords(true);
            }
        });

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWordToList();
            }
        });

        inputText = (EditText) findViewById(R.id.inputText);

        wordList = (ListView) findViewById(R.id.wordList);

        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MainActivity.words);
        wordList.setAdapter(myAdapter);

        wordList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                text = adapterView.getItemAtPosition(i).toString();
                askForDelete(text);
                return false;
            }
        });

    }

    private void switchMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void addWordToList(){
        String text = inputText.getText().toString();
        if(!text.contains(" ")){
            MainActivity.addWord(text);
            myAdapter.notifyDataSetChanged();
        }else{
            utils.showToast(ModifyWordsActivity.this, "No se pudo añadir la palabra a la lista");
        }
    }

    private void askForDelete(String word){
        AlertDialog.Builder dialogAlert = new AlertDialog.Builder(this);
        dialogAlert.setTitle("Deseas eliminar esta palabra de la lista?");

        dialogAlert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.removeWord(word);
                myAdapter.notifyDataSetChanged();
            }
        });
        dialogAlert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogAlert.show();
    }
}
