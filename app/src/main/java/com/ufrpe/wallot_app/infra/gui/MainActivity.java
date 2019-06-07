package com.ufrpe.wallot_app.infra.gui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


import com.ufrpe.wallot_app.R;
import com.ufrpe.wallot_app.infra.Sessao;
import com.ufrpe.wallot_app.usuario.dominio.Usuario;
import com.ufrpe.wallot_app.usuario.negocio.UsuarioServices;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private UsuarioServices usuarioServices = new UsuarioServices();


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Usuario usuario = Sessao.instance.getUsuario();



        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        InicioFragment fragment = new InicioFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Inicio");
        fragmentTransaction.commit();

        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nome_usuario_id);
        txtProfileName.setText(usuario.getNome());
        TextView txtProfileEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email_usuario_id);
        txtProfileEmail.setText(usuario.getEmail());




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.inicio){
            InicioFragment fragment = new InicioFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Inicio");
            fragmentTransaction.commit();
        }
        else if(id == R.id.registro){
            RegistroFragment fragment = new RegistroFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Registro");
            fragmentTransaction.commit();
        }
        else if(id == R.id.grafico){
            GraficoFragment fragment = new GraficoFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Gráfico");
            fragmentTransaction.commit();
        }
        else if(id == R.id.meta){
            MetaFragment fragment = new MetaFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Meta");
            fragmentTransaction.commit();
        }
        else if(id == R.id.orcamento){
            OrcamentoFragment fragment = new OrcamentoFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Orçamento");
            fragmentTransaction.commit();
        }
        else if(id == R.id.setting){
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
        }
        else if(id == R.id.logout){
            usuarioServices.logout();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
