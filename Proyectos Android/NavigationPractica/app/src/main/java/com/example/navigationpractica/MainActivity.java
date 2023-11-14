package com.example.navigationpractica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.navigationpractica.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //Para que muestre el menu del AppBar hay que darle los layouts desde los que será visible el menú,
        //en el resto se dará la opcion de volver al primer layout (Lo que me ha costado  darme cuenta de esto es bárbaro)
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.tabFragment, R.id.drawer2Fragment)
                .setOpenableLayout(binding.drawerLayout)
                .build();
        navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView)).getNavController();
        //Estableciendo la navegación en el Bottom Navigation View
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
        //Estableciendo la navegación en el Navigation View
        NavigationUI.setupWithNavController(binding.navView, navController);
        //Estableciendo la navegación en el toolbar
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

    }

    //Inflando el menú de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Configurando la selección de elementos del Options Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
}