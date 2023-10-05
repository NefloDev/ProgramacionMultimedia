package com.example.juegoadivinapalabra;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private final String WORDSFILEPATH = Environment.getExternalStorageDirectory() + File.separator + "Documents" + File.separator;
    private static final String WORDSFILENAME = "palabras";
    private Button botonJugar;
    private Button botonModificarPalabras;
    public static ArrayList<String> words;

    public static Context context;

    private static final Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        File wordsFile = new File(WORDSFILEPATH);
        wordsFile.mkdir();

        words = loadWords(false);

        botonJugar = (Button) findViewById(R.id.botonJugar);
        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                finish();
            }
        });

        botonModificarPalabras = (Button) findViewById(R.id.botonModificarPalabras);
        botonModificarPalabras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ModifyWordsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public ArrayList<String> loadWords(boolean manual){
        ArrayList<String> wordList = null;
        File wordsFile = new File(WORDSFILEPATH + WORDSFILENAME + ".dat");
        Scanner sc;

        try{
            if(!wordsFile.exists()){
                wordsFile.createNewFile();
            }
            sc = new Scanner(new FileReader(wordsFile));
            wordList = new ArrayList<>();

            while(sc.hasNext()){
                wordList.add(sc.nextLine());
            }

            sc.close();
        }catch(IOException e){
            if(manual){
                utils.showToast(MainActivity.this, "No se pudieron cargar los cambios a la lista");
            }
        }

        return wordList;
    }

    public void saveWords(boolean manual){
        File wordsFile = new File(WORDSFILEPATH + WORDSFILENAME + ".dat");
        FileWriter fW;

        try{
            if(!wordsFile.exists()){
                wordsFile.createNewFile();
            }
            fW = new FileWriter(wordsFile);

            for (String word: words) {
                fW.append(word + "\n");
            }

            fW.close();

        }catch (IOException e){
            if(manual){
                utils.showToast(MainActivity.this, "No se pudieron guardar los cambios a la lista");
            }
        }

    }

    public static void addWord(String word){
        word = firstCapital(word);
        if(!words.contains(word)){
            words.add(word);
        }else{
            utils.showToast(MainActivity.context, "No se pudo añadir la palabra a la lista");
        }
    }

    public static void removeWord(String word){
        words.remove(word);
    }

    private static String firstCapital(String text){
        text = text.toLowerCase();
        text = text.substring(0, 1).toUpperCase() + text.substring(1);
        return text;
    }
}